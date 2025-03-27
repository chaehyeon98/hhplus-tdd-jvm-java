### **커밋 링크**
- 포인트조회 use Mockito : 5e732418c69bc872dad8262579fdfbef2b04bfe2 / https://github.com/chaehyeon98/hhplus-tdd-jvm-java/issues/1
- 포인트충전 : a795f39567ec78289ba9960aa9a1a077a621a08f , 66ce906fb617913a26b15760f22865ff34686dd5 / https://github.com/chaehyeon98/hhplus-tdd-jvm-java/issues/2
- 포인트사용 추가 및 주석 수정 : 987fbf48baf5aa4f9ae5b246b57f3463fe3aeceb / https://github.com/chaehyeon98/hhplus-tdd-jvm-java/issues/3
- 포인트 내역 조회 : 6c010b5d38f1c178656989c68e180f47a86cfd3c / https://github.com/chaehyeon98/hhplus-tdd-jvm-java/issues/4
---
### **리뷰 포인트(질문)**
- 리뷰 포인트 1 : 
  TDD 라이브러리 사용없이(NotUseMockitoServiceImplTest), Mokito 사용하여(PointControllerTest, PointServiceImplTest)
  구현이 제대로 되었는지 검토 부탁드립니다.
- 리뷰 포인트 2 :
  PointController 중 charge, use api 동일한 부분이 있어 
  조회 / 충전 or 사용 / 내역 저장으로 나누었습니다.
  객체지향 방식에서 기능에 따라 이렇게 나누는 것이 응집도를 낮추는게 맞는지 궁금합니다.
  
---
### **이번주 KPT 회고**

### Keep
- 매일 두시간씩 스터디 참여하는 버릇을 들이는 것~~
- 요구사항 분석 및 테스트 시나리오를 먼저 짜보는 것
  (구현전 만들었던 시트~~ https://docs.google.com/spreadsheets/d/1ZPrrk52MYFV8y39-IMegOeMRKVmprlKKYP78x1c_cqY/edit?usp=sharing)
- 여러가지 방법을 고민해보는 것
- 이해가 안가면 청강많이 듣기
  
### Problem
- 레거시 코드에 대한 분석 시간을 줄일 것
- Mokito사용에 익숙해지기
- 발제에 대한 고민은 하루로 만족하기
- git hub push 조심하기~

### Try
- InjectMock으로 controll 테스트에서 service호출이 제대로 되는지 구현해보기
- 여러가지 mock라이브러리 사용하기
