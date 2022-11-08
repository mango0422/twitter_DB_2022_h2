// 최종 수정일 : 2022/11/08
// 최종 수정자 : 염석천
// 수정 내용 : 기본 기능 최초 구현

import java.sql.*;
import java.util.Arrays;

public class DBmain implements main {
    private Connection con = null; // object for connection
    private Statement stmt = null; // object for query
    private PreparedStatement pstmt = null;
    private ResultSet rs = null; // object of dealing with result of query

    private int current_user_id;

    // DBmain 객체 생성 및 Db와 연결, 각 객체는 각 유저로 동작
    // input : 연결할 DB의 주소
    public DBmain(String dbAdd) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbAdd);
            System.out.println(con);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    // 객체의 유저 id 반환
    // input : none
    // output : 유저 id
    public int getUserId() {
        return current_user_id;
    }

    // 회원가입
    // input : ID, 비밀번호, 유저 이름, 본인확인질문, 본인확인답변
    // output : 회원가입 성공 : true, 회원가입 실패 or 로그인 된 상태 : false
    public boolean joinIn(String ID, String pwd, String name, String quiz1, String quiz2) {
        if(current_user_id != -1) {
            try {
                // 쿼리문
                String psql = "insert into user(ID, PW, name,quiz1, quiz2) values (?,?,?,?,?)";
                pstmt = con.prepareStatement(psql);

                // 쿼리문의 가변부분 삽입
                pstmt.setString(1, ID);
                pstmt.setString(2, pwd);
                pstmt.setString(3, name);
                pstmt.setString(4, quiz1);
                pstmt.setString(5, quiz2);

                // 쿼리 execute
                int result = pstmt.executeUpdate();

                if(result > 0)
                    return true;
                else
                    return false;

            } catch(SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if (pstmt != null && pstmt.isClosed()) stmt.close();
                }catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            return false;
        }
    }

    // return 값
    // 로그인 성공 : 유저 id
    // 실패 : -1
    public int logIn(String ID, String pwd) {
        try {
            stmt = con.createStatement();
            String sql = "select ID, PW, i_id from user where ID = " + ID;
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                if (pwd.equals(rs.getString(2))) {
                    this.current_user_id = rs.getInt(3);
                    return current_user_id;
                }
            }
            return -1;
        }catch(SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 비밀번호 변경
    // input : 유저 idx, 변경할 비밀번호
    // output : 성공 : true, 실패(기존 비밀번호와 동일한 경우 포함) : false
    public boolean changePwd(int id, String newPwd) {
        try {
            stmt = con.createStatement();
            String sql = "select PW from user where i_id = "+id;
            rs = stmt.executeQuery(sql);

            if(rs.next()) {
                if(!newPwd.equals(rs.getString(1))) {
                    String psql = "update user set PW = ? where i_id = ?";
                    pstmt = con.prepareStatement(psql);

                    pstmt.setString(1, newPwd);
                    pstmt.setInt(2, id);

                    int result = pstmt.executeUpdate();

                    if(result > 0)
                        return true;
                }
            }

            return false;

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null && pstmt.isClosed()) pstmt.close();
                if (stmt != null && stmt.isClosed()) stmt.close();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 팔로우 추가
    // input : 팔로우 하는 사람 id, 팔로우 할 사람 id
    // output : 성공 : true, 실패 : false
    public boolean follow(int id, int following_id) {
        try {
            String psql = "insert into follow values (?, ?)";
            pstmt = con.prepareStatement(psql);

            pstmt.setInt(1, following_id);
            pstmt.setInt(2, id);

            int result = pstmt.executeUpdate();

            if(result > 0)
                return true;

            return false;

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if(pstmt != null && !pstmt.isClosed()) pstmt.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 팔로워 목록 확인
    // input : 유저 id
    // output : 팔로워 id 리스트, 반횐된 array가 null이면 값 없음
    public int[] showFollower(int id) {
        try {
            stmt = con.createStatement();
            String sql = "select distinct i_id from follow where following_id = " + id;
            rs = stmt.executeQuery(sql);
            int[] result = null;
            int cnt = 0;

            while(rs.next()) {
                if(cnt == 0) {
                    result[cnt] = rs.getInt(1);
                }
                else {
                    result = Arrays.copyOf(result, result.length + 1);
                    result[cnt] = rs.getInt(1);
                }
                cnt++;
            }

            return result;

        }catch(SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 팔로잉 목록 확인
    // input : 유저 id
    // output : 팔로잉 id 리스트
    public int[] showFollowing(int id) {
        try {
            stmt = con.createStatement();
            String sql = "select distinct following_id from follow where i_id = " + id;
            rs = stmt.executeQuery(sql);
            int[] result = null;
            int cnt = 0;

            while(rs.next()) {
                if(cnt == 0) {
                    result[cnt] = rs.getInt(1);
                }
                else {
                    result = Arrays.copyOf(result, result.length + 1);
                    result[cnt] = rs.getInt(1);
                }
                cnt++;
            }

            return result;
        }catch(SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 포스트 작성
    // input : 유저 idx, 포스트 내용(text)
    // output : 성공 true, 실패 false
    public boolean posting(int id, String content){
        try {
            String psql = "insert into post(i_id, text) values (?, ?)";
            pstmt = con.prepareStatement(psql);

            pstmt.setInt(1, id);
            pstmt.setString(2, content);

            int result = pstmt.executeUpdate();

            if(result > 0)
                return true;
            else
                return false;

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if(pstmt != null && !pstmt.isClosed()) pstmt.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 댓글 작성
    // input : 포스트 id, 유저 idx, 댓글 내용(text)
    // output : 성공 true, 실패 false
    public boolean comment(int p_id, int id, String content) {
        try {
            String psql = "insert into comment(p_id, i_id, c_text) values (?, ?, ?)";
            pstmt = con.prepareStatement(psql);

            pstmt.setInt(1, p_id);
            pstmt.setInt(2, id);
            pstmt.setString(3, content);

            int result = pstmt.executeUpdate();

            if(result > 0)
                return true;
            else
                return false;

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if(pstmt != null && !pstmt.isClosed()) pstmt.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
