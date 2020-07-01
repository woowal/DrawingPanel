package main;

import java.awt.Cursor;

import menus.GColorMenu;
import menus.GEditMenu;
import menus.GFileMenu;
import menus.GMenu;
import shape.GEraser;
import shape.GGroup;
import shape.GLine;
import shape.GOval;
import shape.GPen;
import shape.GPolygon;
import shape.GRectangle;
import shape.GShape;

public class GConstants {

	public static final long serialVersionUID = 1L;
	
	public GConstants() {
	}
	
	public enum EMainFrame {
		eWidth(600),
		eHeight(800);
		
		private int value;		
		private EMainFrame(int value) {
			this.value = value;
		}	
		public int getValue() {
			return this.value;
		}
	}
	
	public enum EMenubar {
		eFile(new GFileMenu("파일")),
		eEdit(new GEditMenu("편집")),
		eColor(new GColorMenu("도형"));
		
		private GMenu menu;
		private EMenubar(GMenu menu) {
			this.menu = menu;
		}		
		public GMenu getMenu() {
			return this.menu;
		}
	}
	
	public enum EFileMenu {
		eNew("New","nnew"),
		eOpen("열기","open"),
		eSave("저장","save"),
		eSaveAs("다른 이름으로 저장","saveAs"),
		eQuit("종료","exit");
		
		private String title;	
		private String actionCommand;
		private EFileMenu(String title,String actionCommand) {
			this.title = title;
			this.actionCommand = actionCommand;
		}		
		public String getTitle() {
			return this.title;
		}
		public String getActionCommand() {
			return this.actionCommand;
		}
	}
	
	public enum EEditMenu{
		eUndo("되돌리기","undo"),
		eRedo("다시실행","redo"),
		eCopy("복사","copy"),
		eCut("자르기","cut"),
		ePaste("붙여넣기","paste");
		
		private String title;	
		private String actionCommand;
		private EEditMenu(String title,String actionCommand) {
			this.title = title;
			this.actionCommand = actionCommand;
		}		
		public String getTitle() {
			return this.title;
		}
		public String getActionCommand() {
			return this.actionCommand;
		}
	}
	
	public enum EColorMenu {

		eLineColor("라인 색","setLineColor"),
		eFillColor("채우기 색","setFillColor"),
		elineThick("선 속성","setThick"),
		eForward("앞으로 보내기","forward"),
		eBackward("뒤로 보내기","backward");
		
		private String title;	
		private String actionCommand;
		private EColorMenu(String title,String actionCommand) {
			this.title = title;
			this.actionCommand = actionCommand;
		}		
		public String getTitle() {
			return this.title;
		}
		public String getActionCommand() {
			return this.actionCommand;
		}
	}	
	
	public enum EToolbar {
		eGruop("선택", new GGroup()),
		ePen("펜", new GPen()),
		eEraser("지우개", new GEraser()),
		eRectangle("네모", new GRectangle()),
		eOval("원", new GOval()),
		eLine("라인", new GLine()),
		ePolygon("다각형", new GPolygon());
		
		private String title;
		private GShape tool;
		
		private EToolbar(String title, GShape tool) {
			this.title = title;
			this.tool = tool;
		}		
		public String getTitle() {
			return this.title;
		}
		public GShape getTool() {
			return this.tool;
		}
	}
	
	public final static int MAXPOINTS = 100;
	
	public enum ECursor {
		eDefault(new Cursor(Cursor.DEFAULT_CURSOR)),
		eMove(new Cursor(Cursor.MOVE_CURSOR)),
		eRotate(new Cursor(Cursor.HAND_CURSOR)),
		eEE(new Cursor(Cursor.E_RESIZE_CURSOR)),
		eWW(new Cursor(Cursor.W_RESIZE_CURSOR)),
		eNN(new Cursor(Cursor.N_RESIZE_CURSOR)),
		eSS(new Cursor(Cursor.S_RESIZE_CURSOR)),
		eNE(new Cursor(Cursor.NE_RESIZE_CURSOR)),
		eSE(new Cursor(Cursor.SE_RESIZE_CURSOR)),		
		eNW(new Cursor(Cursor.NW_RESIZE_CURSOR)),
		eSW(new Cursor(Cursor.SW_RESIZE_CURSOR));
		
		private Cursor cursor;
		
		private ECursor(Cursor cursor) {
			this.cursor = cursor;
		}		
		public Cursor getCursor() {
			return this.cursor;
		}
	}
}
