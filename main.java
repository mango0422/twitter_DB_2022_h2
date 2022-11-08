// 최종 수정일 : 2022/11/08
// 최종 수정자 : 염석천
// 수정 내용 : 기본 기능 최초 구현

public interface main {

    // 유저 id 반환 메소드
    // input : none, 단순 호출
    // output : -1 or user_id, -1일 경우 프론트 측에서 작업수헹에 제한 바람
    public int getUserId();
    // 회원가입
    // input : ID, 비밀번호, 유저 이름, 본인확인질문, 본인확인답변
    // output : 회원가입 성공 : true, 회원가입 실패 or 로그인 된 상태 : false
    public boolean joinIn(String ID, String pwd, String name, String quiz1, String quiz2);

    // return 값
    // 로그인 성공 : 유저 id
    // 실패 : -1
    public int logIn(String ID, String pwd);

    // 비밀번호 변경
    // input : 유저 idx, 변경할 비밀번호
    // output : 성공 : true, 실패 : false
    public boolean changePwd(int idx, String newPwd);

    // 팔로우 추가
    // input : 팔로우 하는 사람 id, 팔로우 할 사람 id
    // output : 성공 : true, 실패 : false
    public boolean follow(int id, int following_id);

    // 팔로워 목록 확인
    // input : 유저 id
    // output : 팔로워 id 리스트
    public int[] showFollower(int id);

    // 팔로잉 목록 확인
    // input : 유저 id
    // output : 팔로잉 id 리스트
    public int[] showFollowing(int id);

    // 포스트 작성
    // input : 유저 idx, 포스트 내용(text)
    // output : 성공 true, 실패 false
    public boolean posting(int id, String content);

    // 댓글 작성
    // input : 포스트 id, 유저 idx, 댓글 내용(text)
    // output : 성공 true, 실패 false
    public boolean comment(int p_id, int id, String content);

}