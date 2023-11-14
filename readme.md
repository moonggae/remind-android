# TODO
### Presentation 모듈에서 App 모듈 분리
- 분리할 상세 내용 정리 필요
- route, navigation
- Constants
- DI
- utils

### User 페이지 메뉴
- 필요 기능에 따라 추가 및 삭제
- 개인정보 처리방침
- 회원탈퇴
- Dark Mode 고민중
- Notification 설정

### 친구관리
- 친구 추가시 mind history 페이지 업데이트
- 친구 요청, 요청받음, 삭제 상태에 따른 에러 핸들링
  - 친구 신청 취소시 상대방 페이지에서 수락, 거부 이벤트
  - 친구 요청 수락, 거부시 상대방 페이지에서 취소 이벤트

### 유저 정보 페이지
- 프로필 사진 클릭시 Image Dialog

### Test
- to do learn

# In Progress


# Paused
### 에러 핸들링
- message string 리소스 종속성 문제로 잠시 중단
- network connection, server error, unknown exception 등 global 오류 처리 필요


# Done
### 친구관리
- User 페이지에 메뉴 추가
- Invite 페이지에서 친구 관리 기능 분리
- 친구 삭제 기능 추가
- 유저 프로필 아이콘 클릭시 유저 프로필 페이지로 이동
- 유저 프로필 페이지 변경
  - user 정보 가져올 때 id 가져오기
  - user profile 페이지로 라우딩 할 때 Id 넘겨줘서 user profile viewmodel 에서 데이터 처리하기
  - 유저 관계에 따라 프로필 페이지 분리
  - Me, Friend, Invite 프로필 페이지 이동