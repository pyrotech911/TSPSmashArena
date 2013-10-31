package game;

/**
 * Class for the bullets and projectiles being fired
 * 
 * @author Jacob Charles
 *
 */
public class Shot extends GameObject {
	
	//bullet types
	public static final int GRAV = 1; //subject to gravity (var = grav*100)
	public static final int BOUNCE = 2; //bounces of solids
	public static final int ACCEL = 4; //changes speeds (var = accel*100)
	public static final int PIERCE = 8; //goes through players
	public static final int PHASE = 16; //goes through walls
	public static final int GROW = 32; //changes sizes (var = W/H change per frame)
	public static final int BOMB = 64; //drops an explosion
	private static final int MAX = 127; //sum of all of the above

	private int t; //type
	private int v; //extra variable
	private int l; //life time
	private int s; //source, null means no source, can hurt anyone

	/**
	 * Do-nothing constructor
	 */
	public Shot() {
		super();
	}
	
	/**
	 * Spawn a shot at a location
	 * @param x
	 * 		starting x
	 * @param y
	 * 		starting y
	 */
	public Shot(int x, int y, int source) {
		super(x, y);
		this.s = source;
	}
	
	/**
	 * Create a bullet from a generic type
	 * @param base
	 * 		bullet template
	 * @param source
	 * 		actor source
	 */
	public Shot(ShotModel base, Actor a) {
		this.s = a.getId();
		setSkin(base.getSkin());
		this.t = base.getType();
		this.v = base.getVar();
		this.l = base.getLife();
		setW(base.getW());
		setH(base.getH());
		setVCenter(a.getVCenter());
		setVy(base.getVSpeed());
		
		//left or right?
		if (a.getDir() > 0) {
			setLeftEdge(a.getHCenter());
			setVx(base.getSpeed());
		}
		else {
			setRightEdge(a.getHCenter());
			setVx(-base.getSpeed());
			if (isAccel()) setVar(-getVar());
		}
	}

	/**
	 * Spawn a specified shot type at a location
	 * 
	 * @param x
	 * 		x position of spawn
	 * @param y
	 * 		y position of spawn
	 * @param base
	 * 		shot type
	 */
	public Shot(float x, float y, ShotModel base, int dir) {
		this.s = -1;
		setSkin(base.getSkin());
		this.t = base.getType();
		this.v = base.getVar();
		this.l = base.getLife();
		setW(base.getW());
		setH(base.getH());
		setHCenter(x);
		setVCenter(y);
		
		//is it affected by gravity?
		if (isGravity()) setVy(-base.getSpeed());
		else setVy(0);
		
		//left or right?
		if (dir > 0) {
			setVx(base.getSpeed());
		}
		else {
			setVx(-base.getSpeed());
			if (isAccel()) setVar(-getVar());
		}
	}

	/*getters and setters for attributes*/
	public int getType() {
		return t;
	}
	public void setType(int type) {
		this.t = type;
	}
	public int getVar() {
		return v;
	}
	public void setVar(int var) {
		this.v = var;
	}
	public int getLifeTime() {
		return l;
	}
	public void setLifeTime(int lifeTime) {
		this.l = lifeTime;
	}
	public int getSource() {
		return s;
	}
	public void setSource(int source) {
		this.s = source;
	}
	
	//getters and setters for type modifiers
	public boolean isGravity() {
		return (t&GRAV) > 0;
	}
	public void setGravity(boolean b) {
		if (b) t |= GRAV;
		else t &= MAX-GRAV;
	}
	public boolean isBounce() {
		return (t&BOUNCE) > 0;
	}
	public void setBounce(boolean b) {
		if (b) t |= BOUNCE;
		else t &= MAX-BOUNCE;
	}
	public boolean isAccel() {
		return (t&ACCEL) > 0;
	}
	public void setAccel(boolean b) {
		if (b) t |= ACCEL;
		else t &= MAX-ACCEL;
	}
	public boolean isPierce() {
		return (t&PIERCE) > 0;
	}
	public void setPierce(boolean b) {
		if (b) t |= PIERCE;
		else t &= MAX-PIERCE;
	}
	public boolean isPhase() {
		return (t&PHASE) > 0;
	}
	public void setPhase(boolean b) {
		if (b) t |= PHASE;
		else t &= MAX-PHASE;
	}
	public boolean isGrow() {
		return (t&GROW) > 0;
	}
	public void setGrow(boolean b) {
		if (b) t |= GROW;
		else t &= MAX-GROW;
	}
	public boolean isBomb() {
		return (t&BOMB) > 0;
	}
	public void setBomb(boolean b) {
		if (b) t |= BOMB;
		else t &= MAX-BOMB;
	}
}
