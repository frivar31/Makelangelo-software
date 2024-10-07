package com.marginallyclever.makelangelo.makeart.io;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

public class LoadSratch2Test {
    private static LoadScratch2 loader;

    @BeforeAll
    public static void beforeAll() {
        loader = new LoadScratch2();
    }

    @Test
    public void testCanLoad() {
        // Arrange
        LoadScratch2 loader = new LoadScratch2();

        // Act & Assert
        assertTrue(loader.canLoad("example.sb2"), "Le fichier doit être reconnu comme un fichier Scratch 2 valide.");
        assertTrue(loader.canLoad("example.SB2"), "Il dois aussi marche meme si c'est en majuscule.");
        assertFalse(loader.canLoad("example.txt"), "Les fichiers non-Scratch 2 ne doivent pas être acceptés.");
        assertFalse(loader.canLoad("example.sb3"), "Les fichiers Scratch 3 ne doivent pas être acceptés.");
    }

    @Test
    public void testDoAdd() throws Exception {
        // Arrange
        JSONArray inputArray = new JSONArray();
        inputArray.put(1);
        inputArray.put(2);
        Iterator<?> inputIterator = inputArray.iterator();
        Method doAddMethod = LoadScratch2.class.getDeclaredMethod("doAdd", Iterator.class);
        doAddMethod.setAccessible(true);

        // Act
        double result = (double) doAddMethod.invoke(loader, inputIterator);

        // Assert
        assertEquals(3, result, "1 + 2 devrait être égal à 3.");
    }


    @Test
    public void testDoSubtract() throws Exception {
        // Arrange
        LoadScratch2 loader = new LoadScratch2();
        JSONArray inputArray = new JSONArray();
        inputArray.put(5);
        inputArray.put(3);
        Iterator<?> inputIterator = inputArray.iterator();
        Method doSubtractMethod = LoadScratch2.class.getDeclaredMethod("doSubtract", Iterator.class);
        doSubtractMethod.setAccessible(true);

        // Act
        double result = (double) doSubtractMethod.invoke(loader, inputIterator);

        // Assert
        assertEquals(2, result, "5 - 3 devrait être égal à 2.");
    }


    @Test
    public void testDoMultiply() throws Exception {
        // Arrange
        LoadScratch2 loader = new LoadScratch2();
        JSONArray inputArray = new JSONArray();
        inputArray.put(4);
        inputArray.put(5);
        Iterator<?> inputIterator = inputArray.iterator();
        Method doMultiplyMethod = LoadScratch2.class.getDeclaredMethod("doMultiply", Iterator.class);
        doMultiplyMethod.setAccessible(true);

        // Act
        double result = (double) doMultiplyMethod.invoke(loader, inputIterator);

        // Assert
        assertEquals(20, result, "4 * 5 devrait être égal à 20.");
    }


    @Test
    public void testDoDivide() throws Exception {
        // Arrange
        LoadScratch2 loader = new LoadScratch2();
        JSONArray inputArray = new JSONArray();
        inputArray.put(10);
        inputArray.put(2);
        Iterator<?> inputIterator = inputArray.iterator();
        Method doDivideMethod = LoadScratch2.class.getDeclaredMethod("doDivide", Iterator.class);
        doDivideMethod.setAccessible(true);

        // Act
        double result = (double) doDivideMethod.invoke(loader, inputIterator);

        // Assert
        assertEquals(5, result, "10 / 2 devrait être égal à 5.");
    }


    @Test
    public void testDoModulus() throws Exception {
        // Arrange
        LoadScratch2 loader = new LoadScratch2();
        JSONArray inputArray = new JSONArray();
        inputArray.put(10);
        inputArray.put(3);
        Iterator<?> inputIterator = inputArray.iterator();
        Method doModulusMethod = LoadScratch2.class.getDeclaredMethod("doModulus", Iterator.class);
        doModulusMethod.setAccessible(true);

        // Act
        double result = (double) doModulusMethod.invoke(loader, inputIterator);

        // Assert
        assertEquals(1, result, "10 % 3 devrait être égal à 1.");
    }


    @Test
    public void testDoCompute() throws Exception {
        // Arrange
        LoadScratch2 loader = new LoadScratch2();
        JSONArray inputArray = new JSONArray();
        inputArray.put("sqrt");
        inputArray.put(16);
        Iterator<?> inputIterator = inputArray.iterator();
        Method doComputeMethod = LoadScratch2.class.getDeclaredMethod("doCompute", Iterator.class);
        doComputeMethod.setAccessible(true);

        // Act
        double result = (double) doComputeMethod.invoke(loader, inputIterator);

        // Assert
        assertEquals(4, result, "La racine carrée de 16 devrait être égale à 4.");
    }


    @Test
    public void testResolveValueWithStringNumber() throws Exception {
        // Arrange
        LoadScratch2 loader = new LoadScratch2();
        Method resolveValueMethod = LoadScratch2.class.getDeclaredMethod("resolveValue", Object.class);
        resolveValueMethod.setAccessible(true);

        // Act
        double result = (double) resolveValueMethod.invoke(loader, "25.5");

        // Assert
        assertEquals(25.5, result, "La valeur devrait être 25.5.");
    }

    @Test
    public void testResolveValueWithNumber() throws Exception {
        // Arrange
        LoadScratch2 loader = new LoadScratch2();
        Method resolveValueMethod = LoadScratch2.class.getDeclaredMethod("resolveValue", Object.class);
        resolveValueMethod.setAccessible(true);

        // Act
        double result = (double) resolveValueMethod.invoke(loader, 42.0);

        // Assert
        assertEquals(42.0, result, "La valeur devrait être 42.0.");
    }


    @Test
    public void testResolveValueWithJSONArray() throws Exception {
        // Arrange
        LoadScratch2 loader = new LoadScratch2();
        JSONArray inputArray = new JSONArray();
        inputArray.put("+");
        inputArray.put(5);
        inputArray.put(3);
        Method resolveValueMethod = LoadScratch2.class.getDeclaredMethod("resolveValue", Object.class);
        resolveValueMethod.setAccessible(true);

        // Act
        double result = (double) resolveValueMethod.invoke(loader, inputArray);

        // Assert
        assertEquals(8, result, "La somme de 5 + 3 devrait être 8.");
    }

    @Test
    public void testResolveBooleanGreaterThan() throws Exception {
        // Arrange
        JSONArray jsonArray = new JSONArray("[\">\", 5, 3]");
        Method method = LoadScratch2.class.getDeclaredMethod("resolveBoolean", Object.class);
        method.setAccessible(true);

        // Act
        boolean result = (boolean) method.invoke(loader, jsonArray);

        // Assert
        assertTrue(result, "La condition '5 > 3' devrait être vraie.");
    }


    @Test
    public void testResolveBooleanLessThan() throws Exception {
        // Arrange
        JSONArray jsonArray = new JSONArray("[\"<\", 2, 4]");
        Method method = LoadScratch2.class.getDeclaredMethod("resolveBoolean", Object.class);
        method.setAccessible(true);

        // Act
        boolean result = (boolean) method.invoke(loader, jsonArray);

        // Assert
        assertTrue(result, "La condition '2 < 4' devrait être vraie.");
    }


    @Test
    public void testResolveBooleanEqual() throws Exception {
        // Arrange
        JSONArray jsonArray = new JSONArray("[\"=\", 3, 3]");
        Method method = LoadScratch2.class.getDeclaredMethod("resolveBoolean", Object.class);
        method.setAccessible(true);

        // Act
        boolean result = (boolean) method.invoke(loader, jsonArray);

        // Assert
        assertTrue(result, "La condition '3 = 3' devrait être vraie.");
    }

    @Test
    public void testResolveBooleanNot() throws Exception {
        // Arrange
        JSONArray jsonArray = new JSONArray("[\"not\", [\"=\", 3, 4]]");
        Method method = LoadScratch2.class.getDeclaredMethod("resolveBoolean", Object.class);
        method.setAccessible(true);

        // Act
        boolean result = (boolean) method.invoke(loader, jsonArray);

        // Assert
        assertTrue(result, "La condition 'not(3 = 4)' devrait être vraie.");
    }


    @Test
    public void testResolveBooleanAnd() throws Exception {
        // Arrange
        JSONArray jsonArray = new JSONArray("[\"&\", [\"=\", 3, 3], [\"<\", 2, 4]]");
        Method method = LoadScratch2.class.getDeclaredMethod("resolveBoolean", Object.class);
        method.setAccessible(true);

        // Act
        boolean result = (boolean) method.invoke(loader, jsonArray);

        // Assert
        assertTrue(result, "La condition '(3 = 3) et (2 < 4)' devrait être vraie.");
    }


    @Test
    public void testResolveBooleanOr() throws Exception {
        // Arrange
        JSONArray jsonArray = new JSONArray("[\"|\", [\"=\", 3, 4], [\"<\", 2, 4]]");
        Method method = LoadScratch2.class.getDeclaredMethod("resolveBoolean", Object.class);
        method.setAccessible(true);

        // Act
        boolean result = (boolean) method.invoke(loader, jsonArray);

        // Assert
        assertTrue(result, "La condition '(3 = 4) ou (2 < 4)' devrait être vraie.");
    }

    @Test
    public void testResolveBooleanParseError() throws Exception {
        // Arrange
        Object invalidObj = "invalid input";
        Method method = LoadScratch2.class.getDeclaredMethod("resolveBoolean", Object.class);
        method.setAccessible(true);

        // Act & Assert
        assertThrows(Exception.class, () -> method.invoke(loader, invalidObj), "Une exception devrait être levée pour une entrée invalide.");
    }


}

