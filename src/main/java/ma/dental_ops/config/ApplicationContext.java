package ma.dental_ops.config;

import ma.dental_ops.repository.api.ChargeRepository;
import ma.dental_ops.repository.api.ConsultationRepository;
import ma.dental_ops.repository.api.DossierMedicalRepository;
import ma.dental_ops.repository.api.FileAttenteRepository;
import ma.dental_ops.repository.api.PatientRepository;
import ma.dental_ops.repository.api.RendezVousRepository;
import ma.dental_ops.repository.api.UtilisateurRepository;
import ma.dental_ops.service.api.AuthService;
import ma.dental_ops.service.api.ChargeService;
import ma.dental_ops.service.api.DashboardService;
import ma.dental_ops.service.api.FileAttenteService;
import ma.dental_ops.service.api.PatientService;
import ma.dental_ops.service.api.UtilisateurService;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Contexte IoC minimal de DentalSoft.
 *
 * <p>Les classes concretes proviennent de application.properties. Le contexte
 * les cree par Reflection et injecte leurs dependances par constructeur.</p>
 */
public final class ApplicationContext {

    private static final ApplicationContext INSTANCE = new ApplicationContext();

    private final Map<Class<?>, Object> beans = new HashMap<>();
    private final Properties properties;

    private ApplicationContext() {
        properties = loadProperties();
        initializeBeans();
    }

    public static ApplicationContext getInstance() {
        return INSTANCE;
    }

    public <T> T getBean(Class<T> contract) {
        Object bean = beans.get(contract);
        if (bean == null) {
            throw new IllegalArgumentException("Aucun composant configure pour " + contract.getName());
        }
        return contract.cast(bean);
    }

    private void initializeBeans() {
        JdbcConnectionFactory connectionFactory = JdbcConnectionFactory.getInstance();
        register(JdbcConnectionFactory.class, connectionFactory);

        UtilisateurRepository utilisateurRepository = create(
                "repository.utilisateur", UtilisateurRepository.class,
                new Class<?>[]{JdbcConnectionFactory.class}, connectionFactory);
        PatientRepository patientRepository = create(
                "repository.patient", PatientRepository.class,
                new Class<?>[]{JdbcConnectionFactory.class}, connectionFactory);
        DossierMedicalRepository dossierRepository = create(
                "repository.dossierMedical", DossierMedicalRepository.class,
                new Class<?>[]{JdbcConnectionFactory.class}, connectionFactory);
        FileAttenteRepository fileAttenteRepository = create(
                "repository.fileAttente", FileAttenteRepository.class,
                new Class<?>[]{JdbcConnectionFactory.class}, connectionFactory);
        RendezVousRepository rendezVousRepository = create(
                "repository.rendezVous", RendezVousRepository.class,
                new Class<?>[]{JdbcConnectionFactory.class}, connectionFactory);
        ConsultationRepository consultationRepository = create(
                "repository.consultation", ConsultationRepository.class,
                new Class<?>[]{JdbcConnectionFactory.class}, connectionFactory);
        ChargeRepository chargeRepository = create(
                "repository.charge", ChargeRepository.class,
                new Class<?>[]{JdbcConnectionFactory.class}, connectionFactory);

        register(UtilisateurRepository.class, utilisateurRepository);
        register(PatientRepository.class, patientRepository);
        register(DossierMedicalRepository.class, dossierRepository);
        register(FileAttenteRepository.class, fileAttenteRepository);
        register(RendezVousRepository.class, rendezVousRepository);
        register(ConsultationRepository.class, consultationRepository);
        register(ChargeRepository.class, chargeRepository);

        register(AuthService.class, create(
                "service.auth", AuthService.class,
                new Class<?>[]{UtilisateurRepository.class}, utilisateurRepository));
        register(UtilisateurService.class, create(
                "service.utilisateur", UtilisateurService.class,
                new Class<?>[]{UtilisateurRepository.class}, utilisateurRepository));
        register(PatientService.class, create(
                "service.patient", PatientService.class,
                new Class<?>[]{PatientRepository.class, DossierMedicalRepository.class, JdbcConnectionFactory.class},
                patientRepository, dossierRepository, connectionFactory));
        register(FileAttenteService.class, create(
                "service.fileAttente", FileAttenteService.class,
                new Class<?>[]{FileAttenteRepository.class}, fileAttenteRepository));
        register(DashboardService.class, create(
                "service.dashboard", DashboardService.class,
                new Class<?>[]{RendezVousRepository.class, ConsultationRepository.class},
                rendezVousRepository, consultationRepository));
        register(ChargeService.class, create(
                "service.charge", ChargeService.class,
                new Class<?>[]{ChargeRepository.class}, chargeRepository));
    }

    private <T> void register(Class<T> contract, T bean) {
        beans.put(contract, bean);
    }

    private <T> T create(
            String propertyKey,
            Class<T> contract,
            Class<?>[] parameterTypes,
            Object... arguments) {
        String className = requiredProperty(propertyKey);
        try {
            Class<?> implementation = Class.forName(className);
            if (!contract.isAssignableFrom(implementation)) {
                throw new IllegalStateException(
                        className + " n'implemente pas " + contract.getName());
            }
            Object bean = implementation
                    .getDeclaredConstructor(parameterTypes)
                    .newInstance(arguments);
            return contract.cast(bean);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Classe configuree introuvable : " + className, e);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Constructeur d'injection introuvable sur " + className, e);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Impossible de creer le composant " + className, e);
        }
    }

    private String requiredProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Composant non configure : " + key);
        }
        return value.trim();
    }

    private static Properties loadProperties() {
        Properties loaded = new Properties();
        try (InputStream input = ApplicationContext.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IllegalStateException(
                        "application.properties introuvable. Copiez application.properties.example avant de demarrer.");
            }
            loaded.load(input);
            return loaded;
        } catch (IOException e) {
            throw new IllegalStateException("Impossible de lire application.properties", e);
        }
    }
}
