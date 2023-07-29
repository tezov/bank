
## TODO +split by module and add in config

#-keep class com.tezov.lib_java.debug.DebugLog {
#    *;
#}
#-keep class * {
#    *** toDebugLog*(...);
#    *** toDebugString*(...);
#}
#-keep class com.tezov.lib_java.type.runnable.RunnableGroup {
#    *** name(...);
#}
#-keep class com.tezov.lib_java.type.runnable.RunnableGroup$Action {
#    *** name(...);
#}
