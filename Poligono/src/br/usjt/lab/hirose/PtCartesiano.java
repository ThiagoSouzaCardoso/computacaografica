package br.usjt.lab.hirose;

import java.awt.Graphics;

/**
 * @author thiago.cardoso
 */
public class PtCartesiano {

	public double x;
	public double y;

	public PtCartesiano(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void transladar(int x, int y) {

		this.x += x;
		this.y += y;

	}

	public void escalar(double escala, PtCartesiano pRef) {
		this.x = (this.x * escala + pRef.x * (1 - escala));
		this.y = (this.y * escala + pRef.y * (1 - escala));
	}

	public void retaDeclive(PtCartesiano p2, Graphics g) {

		int p_X1 = (int) this.x;
		int p_Y1 = (int) this.y;
		int p_X2 = (int) p2.x;
		int p_Y2 = (int) p2.y;

		int dX, dY, b;
		int v_FimX = p_X2;
		int v_FimY = p_Y2;
		double x = p_X1, y = p_Y1, m;

		dX = (p_X2 - p_X1);
		dY = (p_Y2 - p_Y1);

		if (dX < 0) {
			x = p_X2;
			y = p_Y2;
			v_FimX = p_X1;
		}

		if (dX != 0 && dY != 0) {

			m = ((double) dY / (double) dX);

			b = (int) (p_Y1 - m * p_X1);

			while (v_FimX > x) {
				x += 1;
				y = (m * x) + b;
				g.drawLine((int) x, (int) y, (int) x, (int) y);
			}
		} else if (dY == 0 && dX == 0) {

			for (; v_FimY > y; y++) {
				g.drawLine((int) x, (int) y, (int) x, (int) y);
			}
			for (; v_FimX > x; x++) {
				g.drawLine((int) x, (int) y, (int) x, (int) y);
			}
		} else if (dX == 0) {
			if (v_FimY < y) {
				double aux = v_FimY;
				v_FimY = (int) y;
				y = aux;
			}
			for (; v_FimY > y; y++) {
				g.drawLine((int) x, (int) y, (int) x, (int) y);
			}

		} else {
			for (; v_FimX > x; x++) {
				g.drawLine((int) x, (int) y, (int) x, (int) y);
			}
		}

	}

	public void retaDDA(PtCartesiano p2, Graphics g) {

		int x, y, erro, deltaX, deltaY;
		erro = 0;

		x = (int) this.x;
		y = (int) this.y;
		deltaX = (int) (p2.x - this.x);
		deltaY = (int) (p2.y - this.y);

		if ((Math.abs(deltaY) >= Math.abs(deltaX) && this.y > p2.y) || (Math.abs(deltaY) < Math.abs(deltaX) && deltaY < 0)) {

			x = (int) p2.x;
			y = (int) p2.y;
			deltaX = (int) (this.x - p2.x);
			deltaY = (int) (this.y - p2.y);
		}
		g.drawLine(x, y, x, y);
		if (deltaX >= 0) {
			if (Math.abs(deltaX) >= Math.abs(deltaY)) {
				for (int i = 1; i < Math.abs(deltaX); i++) {
					if (erro < 0) {
						x++;
						g.drawLine(x, y, x, y);
						erro += deltaY;
					} else {
						x++;
						y++;
						g.drawLine(x, y, x, y);
						erro += deltaY - deltaX;
					}
				}
			} else {
				for (int i = 1; i < Math.abs(deltaY); i++) {
					if (erro < 0) {
						x++;
						y++;
						g.drawLine(x, y, x, y);
						erro += deltaY - deltaX;
					} else {
						y++;
						g.drawLine(x, y, x, y);
						erro -= deltaX;
					}
				}
			}
		} else { // deltaX<0
			if (Math.abs(deltaX) >= Math.abs(deltaY)) {
				for (int i = 1; i < Math.abs(deltaX); i++) {
					if (erro < 0) {
						x--;
						g.drawLine(x, y, x, y);
						erro += deltaY;
					} else {
						x--;
						y++;
						g.drawLine(x, y, x, y);
						erro += deltaY + deltaX;
					}
				}
			} else {
				for (int i = 1; i < Math.abs(deltaY); i++) {
					if (erro < 0) {
						x--;
						y++;
						g.drawLine(x, y, x, y);
						erro += deltaY + deltaX;
					} else {
						y++;
						g.drawLine(x, y, x, y);
						erro += deltaX;
					}
				}
			}
		}
		g.drawLine(x, y, x, y);
	}

	public void retaJava(PtCartesiano p2, Graphics g) {
		g.drawLine((int) this.x, (int) this.y, (int) p2.x, (int) p2.y);
	}

}
