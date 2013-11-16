package game;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Update the buffers in a Controller object using actual key input
 * 
 * @author Jacob Charles
 */

public class RetroControlListener implements KeyListener {
		//controls (may eventually be reconfigurable)
		private static final int KEY_UP = KeyEvent.VK_UP;
		private static final int KEY_DOWN = KeyEvent.VK_DOWN;
		private static final int KEY_LEFT = KeyEvent.VK_LEFT;
		private static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
		private static final int KEY_JUMP = KeyEvent.VK_Z;
		private static final int KEY_FIRE = KeyEvent.VK_X;
		private static final int KEY_START = KeyEvent.VK_ESCAPE;

		private Controller c;
		private String s;

		/**
		 * Initialize a ControlListener to a Controller
		 * 
		 * @param c
		 * 		controller object to update
		 */
		public RetroControlListener(Controller c) {
			this.c = c;
		}
		
		/**
		 * Set a text output
		 * 'null' disables text output
		 * 
		 * @param s
		 * 		string to output to
		 */
		public void setSOut(String s) {
			this.s = s;
		}

		@Override
		public void keyTyped(KeyEvent e) { //TODO: Make this work better
			//text to a string
			if (s != null) {
				char ch = e.getKeyChar();
				if (ch == 8 || ch == 127)
					s= s.substring(0, s.length()-1); //erase a character
				else if (ch > 31 && ch < 127)
					s += ch; //append new typed character
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int k = e.getKeyCode();
			//Set relevant buffers to true
			if (k == KEY_UP) c.setUp(true);
			if (k == KEY_DOWN) c.setDown(true);
			if (k == KEY_LEFT) c.setLeft(true);
			if (k == KEY_RIGHT) c.setRight(true);
			if (k == KEY_JUMP) c.setJump(true);
			if (k == KEY_FIRE) c.setFire(true);
			if (k == KEY_START) c.setStart(true);
			 
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int k = e.getKeyCode();
			//Set relevant buffers to false
			if (k == KEY_UP) c.setUp(false);
			if (k == KEY_DOWN) c.setDown(false);
			if (k == KEY_LEFT) c.setLeft(false);
			if (k == KEY_RIGHT) c.setRight(false);
			if (k == KEY_JUMP) c.setJump(false);
			if (k == KEY_FIRE) c.setFire(false);
			if (k == KEY_START) c.setStart(false);

		}
}
