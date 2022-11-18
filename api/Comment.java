package api;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

public class Comment extends DBConnection {
    private DBConnection dbCon;
    private int p_id;
    private int c_id;
    private int idx;
    private String c_text;
    private Timestamp w_date;

    // 생성자
    public Comment(int p_id, int c_id) {
        this.dbCon = new DBConnection();
        try {
            stmt = con.createStatement();
            String sql = "select * from user where p_id = " + p_id +" and c_id = "+ c_id +";";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                this.p_id = rs.getInt(1);
                this.c_id = rs.getInt(2);
                this.idx = rs.getInt(3);
                this.c_text = rs.getString(4);
                this.w_date = rs.getTimestamp(5);;
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

    public int getC_id() {
        return c_id;
    }

    public int getIdx() {
        return idx;
    }

    public String getC_text() {
        return c_text;
    }

    public Timestamp getW_date() {
        return w_date;
    }

    // 댓글 좋아요 추가 또는 삭제
    // input : 좋아요 누른 사람 id
    // output : 성공 : true, 실패 false
    public boolean CommentLike(int idx) {
        try {
            stmt = con.createStatement();
            String sql = "select * from comment_like where p_id = " + this.p_id + " and c_id = "+ this.c_id + " and l_id = " + idx;
            rs = stmt.executeQuery(sql);

            if(rs.next()) {
                String psql = "delete from comment_like where p_id = ? and c_id = ? and l_id = ?";
                pstmt = con.prepareStatement(psql);

                pstmt.setInt(1, this.p_id);
                pstmt.setInt(2, this.c_id);
                pstmt.setInt(2, idx);

                int result = pstmt.executeUpdate();

                if (result > 0)
                    return true;
                else
                    return false;
            }

            else {
                String psql = "insert into comment_like(p_id, c_id, l_id) values (?, ?, ?)";
                pstmt = con.prepareStatement(psql);

                pstmt.setInt(1, this.p_id);
                pstmt.setInt(2, this.c_id);
                pstmt.setInt(2, idx);

                int result = pstmt.executeUpdate();

                if (result > 0)
                    return true;
                else
                    return false;
            }

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if(pstmt != null && !pstmt.isClosed()) pstmt.close();
                if(stmt != null && !stmt.isClosed()) stmt.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 댓글 좋아요 여부 확인
    // input : 확인할 유저 id
    // output : 좋아요 한 경우 true, 안했거나 실패 false
    public boolean isCommentLike(int idx) {
        try {
            stmt = con.createStatement();
            String sql = "select * from comment_like where p_id = " + this.p_id + "and c_id = " + this.c_id + " and l_id = "+ idx;
            rs = stmt.executeQuery(sql);

            if(rs.next()) return true;

            return false;

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if(stmt != null && !stmt.isClosed()) stmt.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 댓글 좋아요 수 반환
    // input : void
    // 좋아요 수
    public int getNumOfLike() {
        try {
            stmt = con.createStatement();
            String sql = "select count(*) from comment_like where p_id = " + this.p_id + " and c_id = "+ this.c_id + " group by p_id, c_id" ;
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
    public int[] showCommentLikeList() {
        try {
            stmt = con.createStatement();
            String sql = "select l_id from comment_like where p_id = " + this.p_id + " and c_id = "+ this.c_id;
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
