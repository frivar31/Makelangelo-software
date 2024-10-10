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
        // Intention : Vérifier que le chargement des fichiers Scratch 2 fonctionne correctement avec différentes extensions de fichier.

        // Arrange
        LoadScratch2 loader = new LoadScratch2();

        // Act & Assert
        assertTrue(loader.canLoad("example.sb2"), "Le fichier doit être reconnu comme un fichier Scratch 2 valide.");
        assertTrue(loader.canLoad("example.SB2"), "Il dois aussi marche meme si c'est en majuscule.");
        assertFalse(loader.canLoad("example.txt"), "Les fichiers non-Scratch 2 ne doivent pas être acceptés.");
        assertFalse(loader.canLoad("example.sb3"), "Les fichiers Scratch 3 ne doivent pas être acceptés.");
    }

    @Test
    public void testResolveBooleanGreaterThan() throws Exception {
        // Intention : Vérifier que la méthode resolveBoolean identifie correctement que 5 est supérieur à 3.

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
        // Intention : Vérifier que la méthode resolveBoolean identifie correctement que 2 est inférieur à 4.

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
        // Intention : Vérifier que la méthode resolveBoolean identifie correctement que 3 est égal à 3.

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
        // Intention : Vérifier que la méthode resolveBoolean identifie correctement que la négation de '3 = 4' est vraie
        // et la négation de 3 = 3 devrait être false .

        // Arrange
        JSONArray jsonArray = new JSONArray("[\"not\", [\"=\", 3, 4]]");
        JSONArray jsonArray1 = new JSONArray("[\"not\", [\"=\", 3, 3]]");
        Method method = LoadScratch2.class.getDeclaredMethod("resolveBoolean", Object.class);
        method.setAccessible(true);

        // Act
        boolean result = (boolean) method.invoke(loader, jsonArray);
        boolean result1 = (boolean) method.invoke(loader, jsonArray1);
        // Assert
        assertTrue(result, "La condition 'not(3 = 4)' devrait être vraie.");
        assertFalse(result1, "La condition 'not(3 = 3)' devrait être false.");
    }


    @Test
    public void testResolveBooleanAnd() throws Exception {
        // Intention : Vérifier que la méthode resolveBoolean retourne true lorsque les deux conditions sont vraies.

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
        // Intention : Vérifier que la méthode resolveBoolean retourne true lorsque l'une des conditions est vraie.

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
        // Intention : Vérifier que la méthode resolveBoolean lève une exception lorsqu'elle reçoit une entrée invalide.

        // Arrange
        Object invalidObj = "invalid input";
        Method method = LoadScratch2.class.getDeclaredMethod("resolveBoolean", Object.class);
        method.setAccessible(true);

        // Act & Assert
        assertThrows(Exception.class, () -> method.invoke(loader, invalidObj), "Une exception devrait être levée pour une entrée invalide.");
    }

    @Test
    public void testDoAdd() throws Exception {
        // Intention : Vérifier que la méthode doAdd additionne correctement deux nombres.

        // Arrange
        JSONArray inputArray = new JSONArray("[1, 2]");
        JSONArray inputArray1 = new JSONArray("[-4, -2]");
        Iterator<?> inputIterator = inputArray.iterator();
        Iterator<?> inputIterator1 = inputArray1.iterator();

        Method doAddMethod = LoadScratch2.class.getDeclaredMethod("doAdd", Iterator.class);

        doAddMethod.setAccessible(true);

        // Act
        double result = (double) doAddMethod.invoke(loader, inputIterator);
        double result1 = (double) doAddMethod.invoke(loader, inputIterator1);
        // Assert
        assertEquals(3, result, "1 + 2 devrait être égal à 3.");
        assertEquals(-6, result1, "-4 + -2 devrait être égal à -6.");
    }


    @Test
    public void testDoSubtract() throws Exception {
        // Intention : Vérifier que la méthode doSubtract soustrait correctement deux nombres.

        // Arrange
        JSONArray inputArray = new JSONArray("[5, 3]");
        JSONArray inputArray1 = new JSONArray("[-1, 3]");

        Iterator<?> inputIterator = inputArray.iterator();
        Iterator<?> inputIterator1 = inputArray1.iterator();
        Method doSubtractMethod = LoadScratch2.class.getDeclaredMethod("doSubtract", Iterator.class);

        doSubtractMethod.setAccessible(true);

        // Act
        double result = (double) doSubtractMethod.invoke(loader, inputIterator);
        double result1 = (double) doSubtractMethod.invoke(loader, inputIterator1);

        // Assert
        assertEquals(2, result, "5 - 3 devrait être égal à 2.");
        assertEquals(-4, result1, "-1 - 3 devrait être égal à -4.");
    }


    @Test
    public void testDoMultiply() throws Exception {
        // Intention : Vérifier que la méthode doMultiply multiplie correctement deux nombres.

        // Arrange
        JSONArray inputArray = new JSONArray("[4, 5]");
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
        // Intention : Vérifier que la méthode doDivide divise correctement deux nombres.

        // Arrange
        JSONArray inputArray = new JSONArray("[10, 2]");
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
        // Intention : Vérifier que la méthode doModulus calcule correctement le reste de la division.

        // Arrange
        JSONArray inputArray = new JSONArray("[10, 3]");
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
        // Intention : Vérifier que la méthode doCompute calcule correctement la racine carrée d'un nombre.

        // Arrange
        LoadScratch2 loader = new LoadScratch2();
        JSONArray inputArray = new JSONArray("[sqrt, 16]");

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
        // Intention : Vérifier que la méthode resolveValue retourne la valeur numérique d'une chaîne représentant un nombre.

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
        // Intention : Vérifier que la méthode resolveValue retourne correctement la valeur d'un nombre.

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
        // Intention : Vérifier que la méthode resolveValue effectue correctement une addition à partir d'un JSONArray.

        // Arrange
        LoadScratch2 loader = new LoadScratch2();
        JSONArray inputArray = new JSONArray("[\"+\", 5, 3]");
        Method resolveValueMethod = LoadScratch2.class.getDeclaredMethod("resolveValue", Object.class);
        resolveValueMethod.setAccessible(true);

        // Act
        double result = (double) resolveValueMethod.invoke(loader, inputArray);

        // Assert
        assertEquals(8, result, "La somme de 5 + 3 devrait être 8.");
    }


}

