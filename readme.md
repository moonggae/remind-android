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
- Dark Mode 고민중

### Memo
- listen socket for Like
- delete comment

### History
- to refactor pagination
- update calendar ui

### Common
- 페이지 이동 후 렌더링 모션 수정
  - 페이지 이동 하고 렌더링 하니까 안좋아 보임

### 친구 초대
- URL 이벤트 처리

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
- 친구 등록, 삭제 socket 이벤트
- 친구 추가, 삭제시 mind history 페이지 업데이트

### User 페이지
- 프로필 사진 클릭시 Image Dialog
- 프로필 수정시 닉네임 max length, max line 설정
- 회원탈퇴
- open source list

### Notification
- anodrid 13 이상, 12이하 별로 푸쉬알림 Permission 설정
- App이 background 동작시 heads-up 알림

### 개발 하지 않은 기능 제거
- 공유 기능

### Post Detail
- display Memo Comment count
- move to memo detail page

### Home
- padding bottom

### History
- 데이터 없을 때 문구 띄우기
- Calendar View
- to save view type

### Common
- 처음 시작시 인터넷 연결 확인
- App Icon
- On Board 뒤로가기 막기

### Structure
- gitignore secure 파일 업데이트
- local properties 사용