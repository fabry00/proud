package com.proud.console.domain;

import com.proud.commons.DateUtil;
import com.proud.console.Helper;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.junit.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class AppNodeTest {

    private static final Helper helper = new Helper();

    public AppNodeTest() {
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
     * Test of getName method, of class AppNode.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        IAppElement instance = helper.getNode("Node");
        String expResult = "Node";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getType method, of class AppNode.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        IAppElement instance = helper.getNode("Node");
        IAppElement.Type expResult = IAppElement.Type.Node;
        IAppElement.Type result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getType method, of class AppLayer.
     */
    @Test
    public void testIsVirtual() {
        System.out.println("isVirtual");
        IAppElement instance = helper.getNode("Node");
        assertFalse(instance.isVirtual());
    }

    /**
     * Test of getMetric method, of class AppNode.
     */
    @Test
    public void testGetMetric() {
        System.out.println("getMetric");
        DateUtil util = new DateUtil();
        Date now = util.getNowDate();
        AppMetric type = AppMetric.CPU;
        IAppElement instance = helper.getNode("Node");
        instance.addMetricValue(type, now, 5);
        ObservableList<XYChart.Data<Date, Object>> expResult = FXCollections.observableArrayList();
        expResult.add(new XYChart.Data(now, 5));
        ObservableList<XYChart.Data<Date, Object>> result = instance.getMetric(type);
        assertEquals(expResult.get(0).getXValue(), result.get(0).getXValue());
        assertEquals(expResult.get(0).getYValue(), result.get(0).getYValue());
    }

    /**
     * Test of getMetrics method, of class AppNode.
     */
    @Test
    public void testGetMetrics() {
        System.out.println("getMetrics");
        IAppElement instance = helper.getNode("Node");
        DateUtil util = new DateUtil();
        Date now = util.getNowDate();
        instance.addMetricValue(AppMetric.CPU, now, 5);
        instance.addMetricValue(AppMetric.MEMORY, now, 10);

        XYChart.Data<Date, Object> metri1 = new XYChart.Data(now, 5);
        XYChart.Data<Date, Object> metri2 = new XYChart.Data(now, 10);

        Map<AppMetric, ObservableList<XYChart.Data<Date, Object>>> expResult
                = new HashMap<AppMetric, ObservableList<XYChart.Data<Date, Object>>>() {
                    {
                        put(AppMetric.CPU, FXCollections.observableArrayList(metri1));
                        put(AppMetric.MEMORY, FXCollections.observableArrayList(metri2));
                    }
                };

        Map<AppMetric, ObservableList<XYChart.Data<Date, Object>>> result = instance.getMetrics();

        ObservableList<XYChart.Data<Date, Object>> firstSerie = result.get(AppMetric.CPU);
        ObservableList<XYChart.Data<Date, Object>> secondSerie = result.get(AppMetric.MEMORY);

        assertEquals(metri1.getXValue(), firstSerie.get(0).getXValue());
        assertEquals(metri1.getYValue(), firstSerie.get(0).getYValue());
        assertEquals(metri2.getXValue(), secondSerie.get(0).getXValue());
        assertEquals(metri2.getYValue(), secondSerie.get(0).getYValue());
    }

    /**
     * Test of AnomalyDetected method, of class AppNode.
     */
    @Test
    public void testAnomalyDetected() {
        System.out.println("AnomalyDetected");
        IAppElement instance = helper.getNode("Node");
        boolean expResult = false;
        boolean result = instance.AnomalyDetected();
        assertEquals(expResult, result);
    }

    /**
     * Test of AnomalyDetected method, of class AppNode.
     */
    @Test
    public void testAnomalyDetected2() {
        System.out.println("AnomalyDetected2");
        IAppElement instance = new AppNode.Builder("Node").isInAbnormalState().build();
        boolean expResult = true;
        boolean result = instance.AnomalyDetected();
        assertEquals(expResult, result);
    }

    /**
     * Test of FailureDetected method, of class AppNode.
     */
    @Test
    public void testFailureDetected() {
        System.out.println("FailureDetected");
        IAppElement instance = helper.getNode("Node");
        boolean expResult = false;
        boolean result = instance.FailureDetected();
        assertEquals(expResult, result);
    }

    /**
     * Test of FailureDetected method, of class AppNode.
     */
    @Test
    public void testFailureDetected2() {
        System.out.println("FailureDetected2");
        IAppElement instance = new AppNode.Builder("Node").isFailureDetected().build();
        boolean expResult = true;
        boolean result = instance.FailureDetected();
        assertEquals(expResult, result);
    }

    /**
     * Test of IsFineProp method, of class AppNode.
     */
    @Test
    public void testIsFineProp() {
        System.out.println("IsFineProp");
        IAppElement instance = helper.getNode("Node");
        BooleanProperty result = instance.IsFineProp();
        assertTrue(result.get());

    }

    /**
     * Test of IsFineProp method, of class AppNode.
     */
    @Test
    public void testIsFineProp2() {
        System.out.println("IsFineProp2");
        IAppElement instance = new AppNode.Builder("Node").isInAbnormalState().build();
        BooleanProperty result = instance.IsFineProp();
        assertFalse(result.get());

    }

    /**
     * Test of IsFineProp method, of class AppNode.
     */
    @Test
    public void testIsFineProp3() {
        System.out.println("IsFineProp3");
        IAppElement instance = new AppNode.Builder("Node").isFailureDetected().build();
        BooleanProperty result = instance.IsFineProp();
        assertFalse(result.get());

    }

    /**
     * Test of getState method, of class AppNode.
     */
    @Test
    public void testGetState() {
        System.out.println("getState");
        IAppElement instance = helper.getNode("Node");
        IAppElement.State expResult = IAppElement.State.FINE;
        IAppElement.State result = instance.getState();
        assertEquals(expResult, result);
    }

    /**
     * Test of getState method, of class AppNode.
     */
    @Test
    public void testGetState2() {
        System.out.println("getState2");
        IAppElement instance = new AppNode.Builder("Node").isInAbnormalState().build();
        IAppElement.State expResult = IAppElement.State.ANOMALY_DETECTED;
        IAppElement.State result = instance.getState();
        assertEquals(expResult, result);
    }

    /**
     * Test of getState method, of class AppNode.
     */
    @Test
    public void testGetState3() {
        System.out.println("getState3");
        IAppElement instance = new AppNode.Builder("Node").isFailureDetected().build();
        IAppElement.State expResult = IAppElement.State.FAILURE_PREDICTED;
        IAppElement.State result = instance.getState();
        assertEquals(expResult, result);
    }

    /**
     * Test of getInfo method, of class AppNode.
     */
    @Test
    public void testGetInfo() {
        System.out.println("getInfo");
        IAppElement instance = new AppNode.Builder("Node")
                .withInfo(new ElementInfo(ElementInfo.Type.IP, "ip"))
                .build();

        Map<ElementInfo.Type, ElementInfo> result = instance.getInfo();
        assertEquals("ip", result.get(ElementInfo.Type.IP).getValue());
    }

    /**
     * Test of getConnections method, of class AppNode.
     */
    @Test
    public void testGetConnections() {
        System.out.println("getConnections");
        IAppElement connectedNode = helper.getNode("Node2");
        IAppElement instance = new AppNode.Builder("Node").connectedTo(connectedNode).build();
        IAppElement expResult = connectedNode;
        Set<IAppElement> result = instance.getConnections();
        assertEquals(expResult, result.iterator().next());
    }

    /**
     * Test of getNodes method, of class AppNode.
     */
    @Test
    public void testGetNodes() {
        System.out.println("getNodes");
        IAppElement instance = helper.getNode("Node");
        assertTrue(instance.getNodes().isEmpty());
    }

    /**
     * Test of equals method, of class AppNode.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        IAppElement instance = helper.getNode("Node");
        IAppElement instance2 = helper.getNode("Node");
        assertTrue(instance.equals(instance2));
    }

    /**
     * Test of equals method, of class AppNode.
     */
    @Test
    public void testEquals2() {
        System.out.println("equals2");
        IAppElement instance = helper.getNode("Node");
        IAppElement instance2 = helper.getNode("Node2");
        assertFalse(instance.equals(instance2));
    }

    /**
     * Test of equals method, of class AppNode.
     */
    @Test
    public void testEquals3() {
        System.out.println("equals3");
        IAppElement instance = helper.getNode("Element");
        IAppElement instance2 = helper.getLayer("Element");
        assertFalse(instance.equals(instance2));
    }

    /**
     * Test of hashCode method, of class AppNode.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        IAppElement instance = helper.getNode("Node");
        IAppElement instance2 = helper.getNode("Node");
        assertTrue(instance.hashCode() == instance2.hashCode());
    }

    /**
     * Test of hashCode method, of class AppNode.
     */
    @Test
    public void testHashCode2() {
        System.out.println("hashCode2");
        IAppElement instance = helper.getNode("Node");
        IAppElement instance2 = helper.getNode("Node2");
        assertTrue(instance.hashCode() != instance2.hashCode());
    }

    /**
     * Test of hashCode method, of class AppNode.
     */
    @Test
    public void testHashCode3() {
        System.out.println("hashCode3");
        IAppElement instance = helper.getNode("Element");
        IAppElement instance2 = helper.getLayer("Element");
        System.out.println("$$$$$ " + instance.getType() + " -- " + instance2.getType());
        assertTrue(instance.hashCode() != instance2.hashCode());
    }

    /**
     * Test of syncNewData method, of class AppNode.
     */
    @Test
    public void testSyncNewData() {
        System.out.println("syncNewData");
        IAppElement connectedTo = helper.getNode("cnt");
        IAppElement instance = helper.getNode("Node");
        IAppElement instance2 = new AppNode.Builder("Node2")
                .isFailureDetected()
                .withInfo(new ElementInfo(ElementInfo.Type.IP, "ip"))
                .connectedTo(connectedTo)
                .build();

        instance.syncNewData(instance2);

        assertTrue(instance.getName().equals("Node"));
        assertTrue(instance.FailureDetected());
        assertEquals("ip", instance.getInfo().get(ElementInfo.Type.IP).getValue());
        assertEquals(connectedTo, instance.getConnections().iterator().next());
    }
}
