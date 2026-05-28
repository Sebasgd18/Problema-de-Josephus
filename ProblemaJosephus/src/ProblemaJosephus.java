/**
 * Problema del Historiador Josephus - VERSIÓN CORREGIDA
 * Implementación con Lista Circular Simplemente Enlazada
 * 
 * Estudiante: Sebastián Guevara Diaz
 * Parámetros: N = 50, k = 5
 */

class Nodo {
    int valor;
    Nodo siguiente;
    
    public Nodo(int valor) {
        this.valor = valor;
        this.siguiente = null;
    }
}

class ListaCircular {
    private Nodo inicio;
    private int tamaño;
    
    public ListaCircular() {
        this.inicio = null;
        this.tamaño = 0;
    }
    
    /**
     * Construye una lista circular con N nodos numerados del 1 al N
     */
    public void construirCirculo(int N) {
        if (N <= 0) {
            System.out.println("Error: N debe ser mayor que 0");
            return;
        }
        
        // Crear el primer nodo
        inicio = new Nodo(1);
        Nodo actual = inicio;
        tamaño = 1;
        
        // Crear los demás nodos
        for (int i = 2; i <= N; i++) {
            Nodo nuevo = new Nodo(i);
            actual.siguiente = nuevo;
            actual = nuevo;
            tamaño++;
        }
        
        // Cerrar el círculo: el último apunta al primero
        actual.siguiente = inicio;
        
        System.out.println("✓ Círculo creado con " + N + " soldados");
    }
    
    /**
     * Muestra todos los nodos de la lista circular
     */
    public void mostrarCirculo() {
        if (inicio == null) {
            System.out.println("La lista está vacía");
            return;
        }
        
        System.out.print("Círculo actual: ");
        Nodo actual = inicio;
        do {
            System.out.print(actual.valor);
            actual = actual.siguiente;
            if (actual != inicio) {
                System.out.print(" → ");
            }
        } while (actual != inicio);
        System.out.println(" → [vuelve al " + inicio.valor + "]");
    }
    
    /**
     * Resuelve el problema de Josephus
     * VERSIÓN 1: Método original (puede tener el error)
     */
    public int josephusVersion1(int k) {
        if (inicio == null || k <= 0) return -1;
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("VERSIÓN 1 - Método Original");
        System.out.println("=".repeat(60));
        
        Nodo actual = inicio;
        int eliminados = 0;
        
        while (actual.siguiente != actual) {
            // Avanzar k-1 posiciones
            for (int i = 1; i < k; i++) {
                actual = actual.siguiente;
            }
            
            Nodo eliminado = actual.siguiente;
            eliminados++;
            
            if (eliminados <= 10) {
                System.out.printf("Eliminación %2d: Soldado %2d%n", 
                    eliminados, eliminado.valor);
            }
            
            actual.siguiente = eliminado.siguiente;
            actual = actual.siguiente;
        }
        
        System.out.println("Sobreviviente V1: " + actual.valor);
        return actual.valor;
    }
    
    /**
     * Resuelve el problema de Josephus
     * VERSIÓN 2: Método alternativo (verificación)
     */
    public int josephusVersion2(int k) {
        if (inicio == null || k <= 0) return -1;
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("VERSIÓN 2 - Método de Verificación");
        System.out.println("=".repeat(60));
        
        // Reconstruir el círculo para no afectar V1
        ListaCircular temp = new ListaCircular();
        temp.construirCirculo(tamaño);
        
        Nodo actual = temp.inicio;
        int eliminados = 0;
        int nodosRestantes = tamaño;
        
        while (nodosRestantes > 1) {
            // Contar k-1 nodos (no eliminar el actual)
            for (int i = 1; i < k; i++) {
                actual = actual.siguiente;
            }
            
            // El siguiente es el que se elimina
            Nodo eliminado = actual.siguiente;
            eliminados++;
            
            if (eliminados <= 10) {
                System.out.printf("Eliminación %2d: Soldado %2d (quedan %2d)%n", 
                    eliminados, eliminado.valor, nodosRestantes - 1);
            }
            
            // Eliminar el nodo
            actual.siguiente = eliminado.siguiente;
            
            // IMPORTANTE: Avanzar al siguiente nodo para continuar
            actual = actual.siguiente;
            
            nodosRestantes--;
        }
        
        System.out.println("Sobreviviente V2: " + actual.valor);
        return actual.valor;
    }
    
    /**
     * VERSIÓN 3: Usando fórmula matemática de Josephus
     * Esta es la forma más confiable para verificar
     */
    public int josephusFormula(int n, int k) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("VERSIÓN 3 - Fórmula Matemática");
        System.out.println("=".repeat(60));
        
        int resultado = 0;
        for (int i = 2; i <= n; i++) {
            resultado = (resultado + k) % i;
        }
        
        // La fórmula da posición 0-indexed, convertir a 1-indexed
        resultado = resultado + 1;
        
        System.out.println("Sobreviviente V3 (fórmula): " + resultado);
        return resultado;
    }
    
    public int getTamaño() {
        return tamaño;
    }
}

public class ProblemaJosephus {
    
    private static void mostrarBanner() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("║" + " ".repeat(58) + "║");
        System.out.println("║" + " ".repeat(10) + 
            "PROBLEMA DEL HISTORIADOR JOSEPHUS" + 
            " ".repeat(15) + "║");
        System.out.println("║" + " ".repeat(58) + "║");
        System.out.println("═".repeat(60) + "\n");
    }
    
    public static void main(String[] args) {
        mostrarBanner();
        
        final int N = 50;
        final int k = 5;
        
        System.out.println("Estudiante: Sebastián Guevara Diaz");
        System.out.println("Parámetros: N = " + N + ", k = " + k);
        
        // PRUEBA 1: Método original
        ListaCircular lista1 = new ListaCircular();
        lista1.construirCirculo(N);
        int resultado1 = lista1.josephusVersion1(k);
        
        // PRUEBA 2: Método alternativo
        ListaCircular lista2 = new ListaCircular();
        lista2.construirCirculo(N);
        int resultado2 = lista2.josephusVersion2(k);
        
        // PRUEBA 3: Fórmula matemática (GOLD STANDARD)
        ListaCircular lista3 = new ListaCircular();
        lista3.construirCirculo(N);
        int resultado3 = lista3.josephusFormula(N, k);
        
        // COMPARACIÓN FINAL
        System.out.println("\n" + "═".repeat(60));
        System.out.println("📊 COMPARACIÓN DE RESULTADOS");
        System.out.println("═".repeat(60));
        System.out.println("Versión 1 (Original):   Soldado " + resultado1);
        System.out.println("Versión 2 (Alternativa): Soldado " + resultado2);
        System.out.println("Versión 3 (Fórmula):     Soldado " + resultado3);
        
        System.out.println("\n🎯 RESULTADO CORRECTO (según fórmula): Soldado " + resultado3);
        
        if (resultado1 == resultado3 && resultado2 == resultado3) {
            System.out.println("✅ Todas las versiones coinciden - CORRECTO");
        } else {
            System.out.println("⚠️  HAY INCONSISTENCIAS - Revisar implementación");
        }
        
        System.out.println("\n" + "═".repeat(60));
        System.out.println("📋 PARA TU DOCUMENTO:");
        System.out.println("   El sobreviviente correcto es: Soldado " + resultado3);
        System.out.println("═".repeat(60) + "\n");
    }
}