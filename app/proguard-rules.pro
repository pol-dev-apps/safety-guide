# Add project specific ProGuard rules here.

# Keep data models for Room and Gson
-keep class dev.pol.safetyguide.data.model.** { *; }
-keepclassmembers class dev.pol.safetyguide.data.model.** { *; }

# Keep BackupData for Gson serialization
-keep class dev.pol.safetyguide.data.repository.BackupData { *; }
-keepclassmembers class dev.pol.safetyguide.data.repository.BackupData { *; }

# Gson TypeToken
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao class *

# Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }
