package ma.dental_ops;

import junit.framework.TestCase;
import ma.dental_ops.dto.PageResult;
import java.util.ArrayList;

public class PageResultTest extends TestCase {
    public void testTotalPagesRoundsUpAndEmptyResultHasNoPages() {
        assertEquals(3, new PageResult<>(new ArrayList<>(), 0, 10, 21).totalPages());
        assertEquals(0, new PageResult<>(new ArrayList<>(), 0, 10, 0).totalPages());
    }

    public void testItemsAreDefensivelyCopied() {
        var source = new ArrayList<>(java.util.List.of("A"));
        var page = new PageResult<>(source, 0, 10, 1);
        source.clear();
        assertEquals(java.util.List.of("A"), page.items());
    }
}
