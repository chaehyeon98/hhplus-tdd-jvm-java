### **발단**
- TDD 통합 테스트를 진행할시, 동시다발적인 api 호출로 인하여 class의 데이터가 순서대로 업데이트 되지 않는 문제가 생길 수 있음.
- 모든 동작이 마무리된 후 특정한 검증을 해야하는 단계에서 테스트가 되지 않음.
- ex) 포인트 충전 후 사용

### **전개**
- CountDownLatch를 사용하여 비동기 작업에 대한 동기작업 수행
- for문시 동시다발성으로 실행되는 것이 아닌 순서대로 실행 될 수 있기때문에 각 동작별로 ExecutorService을 사용하여 쓰레드 실행

### **위기**
- TDD 테스트 시 비동기 작업에대한 동기작업은 가능하나 api 동시다발적으로 실행할시 class의 대한 접근은 막을 수 없음
- CountDownLatch 중 실행되는 작업은 비동기로 실행되기 때문에 마지막 동작이 아닌, 모든 동작을 검증해야한다면 CountDownLatch를 1로 하여 여러번 사용해야함.

### **결론**
- synchronized와 같이 하나의 스레드만 접근 가능하게 해결할 수 있으나 DB Lock과 유사한 방식임으로 CountDownLatch가 적합해 보였음.
- TDD안에서 동시성이 나타날 수 있는 문제(UserPointTable 동시사용, HistoryTable 동시사용)는 CountDownLatch로 마지막 값 검증할 수 있으므로 CountDownLatch 선택.
