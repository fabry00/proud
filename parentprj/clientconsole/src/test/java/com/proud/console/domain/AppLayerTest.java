package com.proud.console.domain;

import com.proud.console.Helper;
import javafx.collections.ObservableList;
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class AppLayerTest {

    private static final Helper helper = new Helper();

    public AppLayerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getType method, of class AppLayer.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        IAppElement instance = helper.getLayer("Layer");
        IAppElement.Type expResult = IAppElement.Type.Layer;
        IAppElement.Type result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNodes method, of class AppLayer.
     */
    @Test
    public void testGetNodes() {
        System.out.println("getNodes");
        IAppElement instance = helper.getLayer("Layer");
        ObservableList<IAppElement> result = instance.getNodes();
        assertEquals(2, result.size());
        assertEquals("Node", result.get(0).getName());
        assertEquals("Layer2", result.get(1).getName());
    }

}
