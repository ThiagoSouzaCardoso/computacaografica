package br.usjt.lab.hirose;

/**
 * @author thiago.cardoso
 */
public class RotacionarFigura {

	public PtCartesiano rotacionar(PtCartesiano ptOrigem, double angulo, PtCartesiano pivot) {

		double sen = Math.sin(Math.toRadians(angulo));
		double cos = Math.cos(Math.toRadians(angulo));

		double pontoX = (cos * ptOrigem.x) - (sen * ptOrigem.y) + ((pivot.x * (1 - cos) + (pivot.y * sen)));
		double pontoY = (sen * ptOrigem.x) + (cos * ptOrigem.y) + ((pivot.y * (1 - cos)) - (pivot.x * sen));

		ptOrigem.x = (int) pontoX;
		ptOrigem.y = (int) pontoY;

		return ptOrigem;

	}

}
