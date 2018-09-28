# 설비 관리

## 내용

* 설비
  * 공정을 처리할 수 있는 설비를 관리
* 설비 공정 유형
  * 설비에서 취급 가능한 공정유형을 관리

## Release

```
./gradlew release -Prelease.useAutomaticVersion=true
```

## DDL 생성

### 명령어
```
./gradlew generateSchema
```

### 출력 위치
```
build/generated-schema/create.sql
```

## IntelliJ Setting

* Settings
  * Build, Execution, Deployment > Build Tools > Gradle > Runner
    * Delegate IDE build/run actions to Gradle 활성화
