package app.model;

import java.io.Serializable;

public abstract class Familiar implements Serializable {

	// attribut pour la serialisation pour le fonction d'ObjectOutputStream	
    private static final long serialVersionUID = 1L;
	
    // Familiar attributes
    protected String name;

    protected String food;

    protected int hungriness;

    protected int energy;

    protected int hygiene;

    protected int vitality;

    protected Mood mood;

    protected int moodValue;

    protected String familiarType;
    
    protected int portions;
    
    protected TimerPortions timerPortions;
    
    protected TimerEnergy timerEnergy;

    
    
    // Constants

    private static final int MAX_STATS = 100;

    private static final int AMOUNT_OF_STATS = 5;
    
    // food
    private static final int MAX_FEED_PORTION = 35;
    private static final int DECREASE_STATS_PER_ACTION = 5;
    private static final int MAX_PORTIONS = 4;

    // mood
    private static final int HAPPY_THRESHOLD = 85;
    private static final int JOY_THRESHOLD = 70;
    private static final int FINE_THRESHOLD = 55;
    private static final int SAD_THRESHOLD = 35;

    // Constructor called by childs class to init attributes value
    protected Familiar() {
        this.energy = MAX_STATS;
        this.hungriness = MAX_STATS;
        this.hygiene = MAX_STATS;
        this.vitality = MAX_STATS;
        this.mood = Mood.HAPPY;
        this.portions = 2;
        this.timerPortions = new TimerPortions(this);
        this.timerPortions.run();
        this.timerEnergy = new TimerEnergy(this);
        this.timerEnergy.run();
    } 

    protected Familiar(Familiar f) {
        this.energy = f.energy;
        this.hygiene = f.hygiene;
        this.hungriness = f.hungriness;
        this.vitality = f.vitality;
        this.mood = f.mood;
        this.portions = f.portions;
    }
    
    /**
     * Constructeur pour test Timer
     */
    protected Familiar(long period) {
        this.energy = MAX_STATS;
        this.hungriness = MAX_STATS;
        this.hygiene = MAX_STATS;
        this.vitality = MAX_STATS;
        this.mood = Mood.HAPPY;
        this.portions = 2;
        this.timerPortions = new TimerPortions(this, period);
        this.timerEnergy = new TimerEnergy(this, period);
    }

    // Accessors
    public String getFood() {
        return food;
    }

    public int getHungriness() {
        return hungriness;
    }

    public void setHungriness(int hungriness) {
        this.hungriness = hungriness;
    	if (this.hungriness > MAX_STATS)
    		this.hungriness = MAX_STATS;
    }

    public void feed(Rooms currentRoom) {
        if(hungriness < MAX_STATS &&  currentRoom == Rooms.KITCHEN && this.portions > 0){
            // we can only feed him with 35% of the hungriness
            setHungriness(this.hungriness + MAX_FEED_PORTION);
            this.energy -= DECREASE_STATS_PER_ACTION;
            this.hygiene -= DECREASE_STATS_PER_ACTION;
            this.portions--;
        }else{
            // TODO indiquer à l'utilisateur qu'il ne peut pas le nourrir
        }
    }

    public void addPortion()
    {
        if(this.portions < MAX_PORTIONS)
        {
            this.portions++;
        }
    }

    public int getPortions()
    {
        return this.portions;
    }
    
    // utilisee pour les tests
    public void ResetPortion() {
    	this.portions = 0;
    }
    
    // utilisee pour les tests
    public TimerPortions getTimerPortion() {
    	return this.timerPortions;
    }


    public Mood getMood() {
        return mood;
    }


    public void recalculateMood(Weather currentWeather, Rooms currentRoom) {
        moodValue = (int)((hungriness + hygiene + energy + vitality) / AMOUNT_OF_STATS);
        if(currentRoom == Rooms.GARDEN) moodValue*=currentWeather.getCoef();
        
        this.mood = changeMood();
    }

    public Mood changeMood() {

        if(moodValue >= HAPPY_THRESHOLD) return Mood.HAPPY;
        else if(moodValue >= JOY_THRESHOLD) return Mood.JOYFUL;
        else if(moodValue >= FINE_THRESHOLD) return Mood.FINE;
        else if(moodValue >= SAD_THRESHOLD) return Mood.SAD;

        return Mood.MISERABLE;
    }

    public float getMoodCoef() {
        return mood.getCoef();
    }

    public String getFamiliarType() {
        return familiarType;
    }
    
    public void setMoodValue(int moodValue) {
    	this.moodValue = moodValue;
    }
    
    public void setMood(Mood mood) {
    	this.mood = mood;
    }
    
    public int getEnergy() {
    	return this.energy;
    }
    
    public void setEnergy(int energy) {
    	this.energy = energy;
    	if (this.energy > MAX_STATS)
    		this.energy = MAX_STATS;
    }
    
    public TimerEnergy getTimerEnergy() {
    	return this.timerEnergy;
    }
    
    public int getHygiene() {
    	return this.hygiene;
    }
    
    public String getName() {
    	return this.name;
    }
}
