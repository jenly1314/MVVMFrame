## 版本日志

#### v3.0.0：2024-03-03
* 统一改为使用`kotlin`进行了重构
* 移除所有`LiveData`相关代码改用`Flow`
* 更新编译SDK至34
* 更新Gradle至v8.0
* 新增core-ktx依赖（v1.12.0）
* 新增fragment-ktx依赖（v1.6.2）
* 新增lifecycle-ktx相关依赖（v2.7.0）
* 更新Okhttp至v4.12.0
* 更新Hilt至v2.51
* 更新Gson至v2.10.1
* 更新Room至v2.6.1
* 更新retrofit-helper至v1.1.0

#### v2.2.1：2022-04-21
* 更新Okhttp至v4.9.3
* 更新Hilt至v2.41
* 更新Gson至v2.9.0

#### v2.2.0：2021-11-18
* minSdk要求从 16+ 改为 21+
* 更新编译SDK至30
* 更新Gradle至v6.7.1
* 更新Okhttp至v4.9.2
* 更新Hilt至v2.40.1
* 更新Gson至v2.8.9
* 更新Timber至v5.0.1

#### v2.1.1：2021-6-29
* 更新Hilt至v2.37
* 更新Gson至v2.8.7
* 优化细节

#### v2.1.0：2021-4-28  (从v2.1.0开始不再发布至JCenter)
* 更新Hilt至v2.35
* 移除androidx.hilt:hilt-lifecycle-viewmodel [移除原因请查看Dagger v2.34更新说明](https://github.com/google/dagger/releases)
* 更新Lifecycle至v2.3.1
* 更新Room至v2.3.0
* 更新RetrofitHelper至v1.0.1
* 发布至Maven Central

#### v2.0.0：2021-1-15
* 使用Hilt简化Dagger依赖注入用法

#### v1.1.4：2020-12-14
* 优化细节
* 更新Dagger至v2.30.1

#### v1.1.3：2020-6-1
* 支持配置多个BaseUrl，且支持动态改变（详情查看 [RetrofitHelper](https://github.com/jenly1314/RetrofitHelper)）
* 对外暴露更多配置，（详情查看 FrameConfigModule）
* 优化细节
* 更新Retrofit至v2.9.0

#### v1.1.2：2020-4-5
* 优化细节
* 更新Gradle至v5.6.4
* 更新Lifecycle至v2.2.0
* 更新Room至v2.2.5
* 更新Dagger至v2.27
* 更新Retrofit至v2.8.1

#### v1.1.1：2019-11-4
* 优化部分细节
* 更新编译SDK至29
* 更新Gradle至v5.4.1
* 更新Lifecycle至v2.2.0-rc01
* 更新Room至v2.2.1
* 更新Dagger至v2.25.2
* 更新Retrofit至v2.6.2
* 更新Gson至v2.8.6

#### v1.1.0：2019-7-22
* 更新Dagger至v2.23.2
* 更新Gradle至v5.1.1
* 完全迁移至AndroidX版本

#### v1.0.2：2019-7-22
* 更新Dagger至v2.19
* 为迁移至AndroidX做准备（下一版本将直接发布AndroidX版）

#### v1.0.1：2019-7-9
* Retrofit更新至v2.6.0

#### v1.0.0：2018-12-12
* MVVMFrame初始版本
