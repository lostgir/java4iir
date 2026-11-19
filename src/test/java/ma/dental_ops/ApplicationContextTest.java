package ma.dental_ops;

import junit.framework.TestCase;
import ma.dental_ops.config.ApplicationContext;
import ma.dental_ops.config.JdbcConnectionFactory;
import ma.dental_ops.repository.api.PatientRepository;
import ma.dental_ops.repository.jdbcMySql.PatientRepositoryImpl;
import ma.dental_ops.service.api.AuthService;
import ma.dental_ops.service.api.PatientService;
import ma.dental_ops.service.implementation.AuthServiceImpl;
import ma.dental_ops.service.implementation.PatientServiceImpl;

public class ApplicationContextTest extends TestCase {

    public void testConfiguredComponentsAreCreatedByReflection() {
        ApplicationContext context = ApplicationContext.getInstance();

        assertSame(JdbcConnectionFactory.getInstance(),
                context.getBean(JdbcConnectionFactory.class));
        assertEquals(PatientRepositoryImpl.class,
                context.getBean(PatientRepository.class).getClass());
        assertEquals(AuthServiceImpl.class,
                context.getBean(AuthService.class).getClass());
        assertEquals(PatientServiceImpl.class,
                context.getBean(PatientService.class).getClass());
    }
}
