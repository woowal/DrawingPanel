package frames;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GThickChoice extends JFrame{
	
	private ActionHandler actionHandler;
	private JTextField tf;
	private GDrawingPanel drawingpanel;
	
	public GThickChoice (GDrawingPanel drawingpanel) {
		this.drawingpanel = drawingpanel;
		setTitle("선 굵기 선택");
		this.actionHandler = new ActionHandler();
		JButton dot = new JButton("점선");
		JButton line = new JButton("실선");
		JButton sharp = new JButton("각진 선");
		JButton round = new JButton("둥근 선");
		JButton button = new JButton("확인");
		dot.addActionListener(this.actionHandler);
		line.addActionListener(this.actionHandler);
		sharp.addActionListener(this.actionHandler);
		round.addActionListener(this.actionHandler);
		button.addActionListener(this.actionHandler);
		dot.setActionCommand("dot");
		line.setActionCommand("line");
		sharp.setActionCommand("sharp");
		round.setActionCommand("round");
		button.setActionCommand("button");
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		this.tf = new JTextField(10);
		c.add(new JLabel("선 굵기 "));
		c.add(tf);
		c.add(dot);
		c.add(line);
		c.add(sharp);
		c.add(round);
		c.add(button);

		setSize(200,180);
		setVisible(true);
    }

	class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getActionCommand() == "dot") {
				drawingpanel.setLine(false);
			} else if (event.getActionCommand() == "line") {
				drawingpanel.setLine(true);
			} else if (event.getActionCommand() == "sharp") {
				drawingpanel.setCap(0);;
				drawingpanel.setJoin(0);;
			} else if (event.getActionCommand() == "round") {
				drawingpanel.setCap(1);;
				drawingpanel.setJoin(1);;
			} else if (event.getActionCommand() == "button") {
				if(!tf.getText().equals("")) {
					int temp = Integer.parseInt(tf.getText());
					drawingpanel.setLine(temp);
				}
				drawingpanel.changeThick();
				setVisible(false);
			}
		}
	}
}

