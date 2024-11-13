## Modification de la GitHub Action

Pour cette tâche, on a modifié la GitHub Action pour tester notre projet avec cinq configurations différentes de la JVM. L'idée était de pouvoir observer l'impact de chaque flag sur la couverture de code et la performance des tests. On a défini une liste de flags (jvm_flag) dans la matrix, GitHub Actions gère automatiquement chaque exécution avec un flag différent.

### l'implémentation
Pour configurer la matrix, voici comment on a défini les flags :
```yaml
strategy:
    matrix:
        jvm_flag:
          - "-XX:+UseG1GC" 
          - "-XX:+PrintGCDetails"
          - "-XX:+OptimizeStringConcat"
          - "-XX:+UseStringDeduplication"
          - "-XX:+AlwaysPreTouch"

```

Ensuite, chaque flag est appliqué individuellement dans l’étape Build and Test with Maven using JVM Flag. On l’a passé dans MAVEN_OPTS, ce qui permet de le transmettre à la JVM au moment de l’exécution des tests :

```yaml
- name: Build and Test with Maven using JVM Flag
  env:
    MAVEN_OPTS: "${{ matrix.jvm_flag }}"
  run: ./mvnw -B verify
```


## Justification des Flags Choisis

### 1. **`-XX:+UseG1GC`** (type : Garbage Collection)
   - **Description** : Utilise le garbage collector G1, optimisé pour réduire les pauses de mémoire.
   - **Impact attendu** : On s’attend à ce que ce flag améliore la performance en évitant les blocages mémoire, surtout dans les tests intensifs.

### 2. **`-XX:+PrintGCDetails`** (type : Observabilité)
   - **Description** : Affiche des informations détaillées sur le garbage collection.
   - **Impact attendu** :  Nous aide à comprendre comment la mémoire est gérée et à repérer des problèmes de performance liés à la gestion de la mémoire

### 3. **`-XX:+OptimizeStringConcat`** (type : Optimisation de performance)
   - **Description** : Optimise la concaténation de chaînes pour limiter les allocations mémoire inutiles.
   - **Impact attendu** :  Réduit l’utilisation de la mémoire dans les cas où on travaille beaucoup avec des chaînes de caractères, comme dans certains tests

### 4. **`-XX:+UseStringDeduplication`** (type : Optimisation mémoire)
   - **Description** : Supprime les chaînes en double dans la mémoire pour limiter la consommation de RAM.
   - **Impact attendu** :  Diminue l’empreinte mémoire, ce qui est utile dans les projets traitant des données de texte.

### 5. **`-XX:+AlwaysPreTouch`** (type : Latence d'initialisation)
   - **Description** : Pré-alloue la mémoire lors du démarrage pour réduire la latence d’allocation.
   - **Impact attendu** : Optimise les performances au démarrage, en limitant la fragmentation mémoire.

## Structure des Logs

On a ajouté une étape de log pour chaque flag afin de rendre chaque exécution plus lisible et identifiable dans les logs. Cela permet de voir précisément quel flag est utilisé pour chaque build.

