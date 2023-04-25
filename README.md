# Goal-Achievement-Tree 

Achieve your goal in 10 steps and grow your tree, which will be a consolidation of the fact that you were able to achieve your goal, for each completed step towards the goal, the tree will grow. As a result, the tree will grow as much as possible as you reach your goal. (All data is stored in a database on your local device. The trees in this program are farktal trees with a limited depth of 10. Each tree is unique, there are no templates, only the input data that will be used to generate the tree. The app was created for educational purposes.)

Screenshots
---------------
#### Light Theme
<img src="https://github.com/ICalmPersonI/Goal-Achievement-Tree/blob/master/screenshots/light/1.png" alt="drawing" width="150" height="300"/> <img src="https://github.com/ICalmPersonI/Goal-Achievement-Tree/blob/master/screenshots/light/2.png" alt="drawing" width="150" height="300"/> <img src="https://github.com/ICalmPersonI/Goal-Achievement-Tree/blob/master/screenshots/light/3.png" alt="drawing" width="150" height="300"/> 
<img src="https://github.com/ICalmPersonI/Goal-Achievement-Tree/blob/master/screenshots/light/4.png" alt="drawing" width="150" height="300"/> <img src="https://github.com/ICalmPersonI/Goal-Achievement-Tree/blob/master/screenshots/light/5.png" alt="drawing" width="150" height="300"/> <img src="https://github.com/ICalmPersonI/Goal-Achievement-Tree/blob/master/screenshots/light/6.png" alt="drawing" width="150" height="300"/> 
#### Dark Theme

<img src="https://github.com/ICalmPersonI/Goal-Achievement-Tree/blob/master/screenshots/night/1.png" alt="drawing" width="150" height="300"/> <img src="https://github.com/ICalmPersonI/Goal-Achievement-Tree/blob/master/screenshots/night/2.png" alt="drawing" width="150" height="300"/> <img src="https://github.com/ICalmPersonI/Goal-Achievement-Tree/blob/master/screenshots/night/3.png" alt="drawing" width="150" height="300"/> 
<img src="https://github.com/ICalmPersonI/Goal-Achievement-Tree/blob/master/screenshots/night/4.png" alt="drawing" width="150" height="300"/> <img src="https://github.com/ICalmPersonI/Goal-Achievement-Tree/blob/master/screenshots/night/5.png" alt="drawing" width="150" height="300"/> <img src="https://github.com/ICalmPersonI/Goal-Achievement-Tree/blob/master/screenshots/night/6.png" alt="drawing" width="150" height="300"/> 
<img src="https://github.com/ICalmPersonI/Goal-Achievement-Tree/blob/master/screenshots/night/7.png" alt="drawing" width="150" height="300"/> <img src="https://github.com/ICalmPersonI/Goal-Achievement-Tree/blob/master/screenshots/night/8.png" alt="drawing" width="150" height="300"/><img src="https://github.com/ICalmPersonI/Goal-Achievement-Tree/blob/master/screenshots/night/9.png" alt="drawing" width="150" height="300"/>


Demo
---------------
https://user-images.githubusercontent.com/87424785/234356050-12f9be6b-f29b-49d4-a154-ece964b2837c.mp4

Tech Stack
---------------
- Minimum SDK level 21
- [Jetpack Compose](https://developer.android.com/jetpack/compose/documentation)
- [Jetpack Compose Navigation](https://developer.android.com/jetpack/compose/navigation)
- [Compose Reorderable](https://github.com/aclassen/ComposeReorderable)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Kotlin coroutines](https://developer.android.com/kotlin/coroutines)
- [Material 2](https://m2.material.io)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [Hilt](https://dagger.dev/hilt)

The tree generation parameters are defined by constants in com/calmperson/goalachievementtree/model/fractaltree/FractalTreeGenerator.kt.
```kotlin
    companion object {
        const val ANGLE = 90f // The angle at which the tree begins to grow.
        const val DEPTH = 11
        const val MIN_BRANCH_LENGTH_MULTIPLIER = 5
        const val MAX_BRANCH_LENGTH_MULTIPLIER = 20
        const val MIN_DEVIATION = 15 // The minimum possible deviation from the previous angle.
        const val MAX_DEVIATION = 30 // The maximum possible deviation from the previous angle.
    }
```
