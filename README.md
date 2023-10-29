# Dessert-Release
This application displays a list of Android Releases in form of layout or grid depending on the user's preference saved with Preferences Datastore. The user can also change between dark and light mode.

The aplication uses these Jetpack libraries:
- Preferences Datastore to persist key-value pairs of data in the phone storage.
- ViewModel to implement MVVM UI design pattern.
- Dagger-Hilt for dependency injection.
- Lifecycle KTX and Lifecycle Compose to collect the StateFlow from the ViewModel in a lifecycle aware manner inside the composables using `collectAsStateWithLifecycle()` API.

![Frame 3](https://github.com/Camilo-Hernandez/Dessert-Release/assets/36543483/48d11410-44dd-4027-a80d-27931a190a3d)
