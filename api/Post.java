package api;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

public class Post extends DBConnection {
    private DBConnection dbCon;
    private int p_id;
    private int idx;
    private String text;
    private Timestamp time;
    private String m_status;

    // 생성자
    public Post(int p_id) {
        this.dbCon = new DBConnection();
        try {
            stmt = con.createStatement();
            String sql = "select * from user where p_id = " + p_id +";";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                this.p_id = rs.getInt(1);
                this.idx = rs.getInt(2);
                this.text = rs.getString(3);
                this.time = rs.getTimestamp(4);
                this.m_status = rs.getString(5);;
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

    // getter
    // 각자 값 반환
    public int getP_id() {
        return p_id;
    }

    public int getIdx() {
        return idx;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getM_status() {
        return m_status;
    }

    // 포스트 수정
    // input : 수정 하는 사람 id, 수정할 내용
    // output : 성공 true, 실패 false
    public boolean modifyPost(int idx, String text) {
        if(this.idx != idx)
            return false;
        try {
            String psql = "update post set text = ?, m_status = '1' where p_id = ?";
            pstmt = con.prepareStatement(psql);

            pstmt.setString(1, text);
            pstmt.setInt(2, this.p_id);

            int result = pstmt.executeUpdate();

            if(result > 0) {
                this.text = text;
                this.m_status = "1";
                return true;
            }
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
    // input : 유저 idx, 댓글 내용(text)
    // output : 성공 true, 실패 false
    public boolean writeComment(int id, String content) {
        try {
            String psql = "insert into comment(p_id, idx, c_text) values (?, ?, ?)";
            pstmt = con.prepareStatement(psql);

            pstmt.setInt(1, this.p_id);
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

    // 댓글 리스트 반환
    // input : void
    // output : 댓글 c_id 리스트, 없으면 null
    public int[] showCommentList() {
        try {
            stmt = con.createStatement();
            String sql = "select c_id from comment where p_id = " + this.p_id;
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

    // 포스트 댓글 수 반환
    // input : void
    // output : 포스트 댓글 수
    public int getNumOfComment() {
        try {
            stmt = con.createStatement();
            String sql = "select count(*) from post join comment using (p_id) where p_id = " + this.p_id + " group by p_id" ;
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

    // 포스트 좋아요 추가
    // input : 좋아요 누른 사람 id
    // output : 성공 : true, 실패 false
    public boolean addPostLike(int idx) {
        try {
            String psql = "insert into post_like(p_id, l_id) values (?, ?)";
            pstmt = con.prepareStatement(psql);

            pstmt.setInt(1, this.p_id);
            pstmt.setInt(2, idx);

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

    // 포스트 좋아요 수 반환
    // input : void
    // 좋아요 수
    public int getNumOfLike() {
        try {
            stmt = con.createStatement();
            String sql = "select count(*) from post join post_like using (p_id) where p_id = " + this.p_id + " group by p_id" ;
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

    // 좋아요 리스트 반환
    // input : void
    // output : 좋아요 l_id 리스트, 없으면 null
    public int[] showPostLikeList() {
        try {
            stmt = con.createStatement();
            String sql = "select l_id from post_like where p_id = " + this.p_id;
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

}
