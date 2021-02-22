package model.board.dto;

import java.sql.Timestamp;

public class BoardTypeDTO {
    
    // Field
    private int board_type_no;
    private String board_type;
    private String board_type_name;
    private String board_type_use;
    private Timestamp board_type_regi_date;
    
    // Constructor
    public BoardTypeDTO() {}

    // Method
    public int getBoard_type_no() {
        return board_type_no;
    }

    public void setBoard_type_no(int board_type_no) {
        this.board_type_no = board_type_no;
    }

    public String getBoard_type() {
        return board_type;
    }

    public void setBoard_type(String board_type) {
        this.board_type = board_type;
    }

    public String getBoard_type_name() {
        return board_type_name;
    }

    public void setBoard_type_name(String board_type_name) {
        this.board_type_name = board_type_name;
    }

    public String getBoard_type_use() {
        return board_type_use;
    }

    public void setBoard_type_use(String board_type_use) {
        this.board_type_use = board_type_use;
    }

    public Timestamp getBoard_type_regi_date() {
        return board_type_regi_date;
    }

    public void setBoard_type_regi_date(Timestamp board_type_regi_date) {
        this.board_type_regi_date = board_type_regi_date;
    }
    

}
