# Сryptocurrency rates android application 
The sample application written entirely in Kotlin and uses the Gradle build system.

# Features
The app displays a list of cryptocurrencies rates. 

For each currency, the following is displayed:
- Logo
- Name of the coin
- Currency symbol
- Price of the coin
- Market capitalization. Price times circulating supply
- 24h trade volume (graph)

Sorting by:
- Price of the coin
- 24h trade volume

# Development Environment
The app is written entirely in Kotlin and uses the Gradle build system and uses Jetpack's
[Android Ktx extensions](https://developer.android.com/kotlin/ktx).

Asynchronous tasks are handled with
[coroutines](https://developer.android.com/kotlin/coroutines). Coroutines allow for simple
and safe management of one-shot operations as well as building and consuming streams of data using
[Kotlin Flows](https://developer.android.com/kotlin/flow).

Dependency Injection is implemented with
[Hilt](https://developer.android.com/training/dependency-injection/hilt-android).

[Room](https://developer.android.com/jetpack/androidx/releases/room) is used
for store data local.

[Retrofit](https://square.github.io/retrofit/) is used
for recive data from API.

[Paging library 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) is used
for displaing paging from a layered data source: network API data source with a local database cache (Room).

All build scripts are written with the
[Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html).

# Build
To build the app, use the `gradlew build` command or use "Import Project" in Android Studio. Android Studio Arctic Fox or newer is required.


