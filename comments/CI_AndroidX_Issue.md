CI is failing because AndroidX is not enabled. Please consider adding the following lines to your `gradle.properties`:

```
android.useAndroidX=true
android.enableJetifier=true
```

This should help resolve the issues with the build process.