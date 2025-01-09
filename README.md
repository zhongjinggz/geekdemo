# geekdemo
[极客时间《手把手教你落地DDD》](https://time.geekbang.org/column/intro/100311801?tab=intro)配套代码

## 分支说明：
- main: 主分支，会持续更新，目前更新至21课(统一用builder风格) 
- iteration-1: 迭代一完成的状态（第12课）
- iteration-2-till-lesson21: 至21课的代码(Org 用builder风格，Emp用 assembler 风格)
- iteration-2-lesson21-builder-style: 至21课代码，统一用builder风格

## 使用说明
- 单元测试: mvn test
- 集成测试: mvn failsafe:integration-test
- 集成测试&单元测试: mvn verify
- 运行:
    - mvn spring-boot:run
    - localhost:8080/swagger-ui/index.html
