
# Tâche #3 :
### Membres de l'équipe :
- Tarek Mekki Daouadji 20174482
- Ibrahim Melzi 20066033

### Liste et choix des flags utilisés :

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

### Modification de la Github Action

On a modifié le fichier `.github/workflows/test.yml` afin d’ajouter une stratégie de compilation et de test avec différents flags JVM.

#### Code ajouté :

Dans la section `jobs -> build` :
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
  
Dans la section `jobs -> build -> steps`, j’ai ajouté une étape pour afficher le flag JVM actuellement utilisé et le taux de couverture des test comme demander:
```yaml
- name: Log JVM Flag and Coverage
  run: |
    echo "Running with JVM flag: ${{ matrix.jvm_flag }}: $COVERAGE%"
```

### Humour

On a ajouter un message d'humour dans les logs :
```yaml
name: Humour
        run: |
          echo "Parfois, j’aimerais un garbage collector dans ma tête… pour supprimer immédiatement certaines personnes lol. 😂😂🗑️💀"
```

