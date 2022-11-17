package api;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;

public class User extends DBConnection {

    private DBConnection dbCon;
    private int idx; // integer id
    private String ID; // ID
    private String PW; // PW
    private String i_img; // profile image url
    private String i_text; // user text
    private String name; // user name
    private String quiz; // user identifying question
    private String answer; // answer of question
    private Date sg_time; // time that signed-up

    public User() {
        this.dbCon = new DBConnection();
        this.idx = -1;
        this.i_img = null;
        this.i_text = null;
        this.name = null;
        this.quiz = null;
        this.answer = null;
        this.sg_time = null;
    }

    public User(int idx) {
        try {
            stmt = con.createStatement();
            String sql = "select (PW, i_img, i_text, name, quiz, answer, sg_time, ID) from user where idx = \'" + idx +"\';";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                this.PW = rs.getString(1);
                this.idx = rs.getInt(2);
                this.i_img = rs.getString(3);
                this.i_text = rs.getString(4);
                this.name = rs.getString(5);
                this.quiz = rs.getString(6);
                this.answer = rs.getString(7);
                this.sg_time = rs.getDate(8);
                this.ID = rs.getString(9);

            }
        }catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean joinIn(String ID, String pwd, String name, String quiz, String answer) {
        if(idx != -1) {
            try {
                // 쿼리문
                String psql = "insert into user(ID, PW, name, quiz, answer) values (?,?,?,?,?)";
                pstmt = con.prepareStatement(psql);

                // 쿼리문의 가변부분 삽입
                pstmt.setString(1, ID);
                pstmt.setString(2, pwd);
                pstmt.setString(3, name);
                pstmt.setString(4, quiz);
                pstmt.setString(5, answer);

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
    // 로그인 성공 : true
    // 실패 : false
    public boolean logIn(String ID, String pwd) {
        try {
            stmt = con.createStatement();
            String sql = "select * from user where ID = \'" + ID + "\';";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                if (pwd.equals(rs.getString(2))) {
                    this.idx = rs.getInt(3);
                    this.i_img = rs.getString(4);
                    this.i_text = rs.getString(5);
                    this.name = rs.getString(6);
                    this.quiz = rs.getString(7);
                    this.answer = rs.getString(8);
                    this.sg_time = rs.getDate(9);
                    this.ID = rs.getString(1);
                    this.PW = pwd;
                    return true;
                }
            }
            return false;
        }catch(SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    // getter
    // input void
    // output 있으면 해당 값 없으면 null
    public int getIdx() {
        return idx;
    }

    public String getID() {
        return ID;
    }

    public String getPW() {
        return PW;
    }

    public String getI_img() {
        return i_img;
    }

    public String getI_text() {
        return i_text;
    }

    public String getName() {
        return name;
    }

    public String getQuiz() {
        return quiz;
    }

    public String getAnswer() {
        return answer;
    }

    public Date getSg_time() {
        return sg_time;
    }

    // 비밀번호 변경
    // input : 유저 idx, 변경할 비밀번호
    // output : 성공 : true, 실패(기존 비밀번호와 동일한 경우 포함) : false
    public boolean setPW(String newPW) {
        if(this.idx == -1)
            return false;
        try {
            stmt = con.createStatement();
            String sql = "select PW from user where idx = "+ this.idx;
            rs = stmt.executeQuery(sql);

            if(rs.next()) {
                if(!newPW.equals(rs.getString(1))) {
                    String psql = "update user set PW = ? where idx = ?";
                    pstmt = con.prepareStatement(psql);

                    pstmt.setString(1, newPW);
                    pstmt.setInt(2, this.idx);

                    int result = pstmt.executeUpdate();

                    if(result > 0) {
                        this.PW = newPW;
                        return true;
                    }
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

    // 프로필사진 변경
    // input : 변경할 사진 url
    // output : 성공 : true, 실패 : false
    public boolean setI_img(String newI_img) {
        if(this.idx == -1)
            return false;
        try {

            String psql = "update user set i_img = ? where idx = ?";
            pstmt = con.prepareStatement(psql);

            pstmt.setString(1, newI_img);
            pstmt.setInt(2, this.idx);

            int result = pstmt.executeUpdate();

            if(result > 0) {
                this.i_img = newI_img;
                return true;
            }

            return false;

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null && pstmt.isClosed()) pstmt.close();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 한줄소개 변경
    // input : 변경할 한줄 소개
    // output : 성공 : true, 실패 : false
    public boolean setI_text(String newI_text) {
        if(this.idx == -1)
            return false;
        try {

            String psql = "update user set i_text = ? where idx = ?";
            pstmt = con.prepareStatement(psql);

            pstmt.setString(1, newI_text);
            pstmt.setInt(2, this.idx);

            int result = pstmt.executeUpdate();

            if(result > 0) {
                this.i_text = newI_text;
                return true;
            }

            return false;

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null && pstmt.isClosed()) pstmt.close();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 이름 변경
    // input : 변경할 이름
    // output : 성공 : true, 실패 : false
    public boolean setName(String newName) {
        if(this.idx == -1)
            return false;
        try {

            String psql = "update user set name = ? where idx = ?";
            pstmt = con.prepareStatement(psql);

            pstmt.setString(1, newName);
            pstmt.setInt(2, this.idx);

            int result = pstmt.executeUpdate();

            if(result > 0) {
                this.name = newName;
                return true;
            }

            return false;

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null && pstmt.isClosed()) pstmt.close();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 팔로우 추가
    // input : 팔로우 할 사람 id
    // output : 성공 : true, 실패 : false
    public boolean addFollow(int following_id) {
        if(this.idx == -1)
            return false;
        try {
            String psql = "insert into follow values (?, ?)";
            pstmt = con.prepareStatement(psql);

            pstmt.setInt(1, following_id);
            pstmt.setInt(2, this.idx);

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
    // input : void
    // output : 팔로워 id 리스트, 반횐된 array가 null이면 값 없음
    public int[] showFollowerList() {
        if(this.idx == -1)
            return null;
        try {
            stmt = con.createStatement();
            String sql = "select distinct idx from follow where following_id = " + this.idx;
            rs = stmt.executeQuery(sql);
            int[] result = null;
            int cnt = 0;

            while(rs.next()) {
                if(cnt == 0) {
                    result = new int[1];
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
    // input : void
    // output : 팔로잉 id 리스트, 반환된 array가 null이면 값 없음
    public int[] showFollowingList() {
        if(this.idx == -1)
            return null;
        try {
            stmt = con.createStatement();
            String sql = "select distinct following_id from follow where idx = " + this.idx;
            rs = stmt.executeQuery(sql);
            int[] result = null;
            int cnt = 0;

            while(rs.next()) {
                if(cnt == 0) {
                    result = new int[1];
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
    public boolean writePost(String content){
        if(this.idx == -1)
            return false;
        try {
            String psql = "insert into post(idx, text) values (?, ?)";
            pstmt = con.prepareStatement(psql);

            pstmt.setInt(1, this.idx);
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

    // 유저 포스트 조회
    // input : void
    // output : 포스트 id 리스트
    public int[] showPostList() {
        if(this.idx == -1)
            return null;
        try {
            stmt = con.createStatement();
            String sql = "select p_id from post where idx = " + this.idx + " order by time desc";
            rs = stmt.executeQuery(sql);
            int[] result = null;
            int cnt = 0;

            while(rs.next()) {
                if(cnt == 0) {
                    result = new int[1];
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

    // 유저 메인 페이지(로그인 된 경우 : 팔로잉 한 사람들의 포스트 최근 것 부터, 로그인 안 된 경우 : 모든 포스트 최근 것부터)
    // input : void
    // output : 포스트 id 리스트
    public int[] showMainPagePostList() {
        try {
            stmt = con.createStatement();
            String sql;
            if(idx == -1) sql = "select p_id from post order by time desc";
            else sql = "select post.p_id from follow join post on follow.following_id = post.idx where follow.idx = " + this.idx + " order by post.time desc";
            rs = stmt.executeQuery(sql);
            Integer wrapper;
            int[] result = null;
            int cnt = 0;

            while(rs.next()) {
                if(cnt == 0) {
                    result = new int[1];
                }
                else {
                    result = Arrays.copyOf(result, result.length + 1);

                }
                wrapper = rs.getInt(1);
                result[cnt] = wrapper.intValue();
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

    // 팔로워 수 반환
    // input : void
    // output : 팔로워 수
    public int getNumOfFollower() {
        try {
            stmt = con.createStatement();
            String sql = "select count(*) from follow where following_id = " + this.idx + " group by following_id" ;
            rs = stmt.executeQuery(sql);
            int result;

            rs.next();
            result = rs.getInt(1);

            return result;

        }catch(SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 팔로잉 수 반환
    // input : void
    // output : 팔로잉 수
    public int getNumOfFollowing() {
        try {
            stmt = con.createStatement();
            String sql = "select count(*) from follow where idx = " + this.idx + " group by idx" ;
            rs = stmt.executeQuery(sql);
            int result;

            rs.next();
            result = rs.getInt(1);

            return result;

        }catch(SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
