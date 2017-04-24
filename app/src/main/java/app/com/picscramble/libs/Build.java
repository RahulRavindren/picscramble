package app.com.picscramble.libs;

import android.content.pm.PackageInfo;

import app.com.picscramble.BuildConfig;


public final class Build {

        private final PackageInfo packageInfo;

        public Build(final PackageInfo packageInfo) {
            this.packageInfo = packageInfo;
        }

        public static boolean isInternal() {return  BuildConfig.FLAVOR.equals("internal");}

        public static boolean isExternal() {return  !isInternal();}

        public static boolean isDebug() {
            return BuildConfig.BUILD_TYPE.equals("debug");
        }

        public static boolean isRelease() {
            return BuildConfig.BUILD_TYPE.equals("release");
        }

        public static boolean isDev() {
            return BuildConfig.BUILD_TYPE.equals("dev");
        }

        public String getapplicationId() {
            return packageInfo.packageName;
        }

        public String sha() {return  "";}

        public Integer versionCode() {return packageInfo.versionCode;}

        public String versionName() {return packageInfo.versionName;}


}
