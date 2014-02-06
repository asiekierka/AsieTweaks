package pl.asie.tweaks.record;

public class RecordData {
	private String name, soundName, color;
	public final boolean spawnInDungeons = false;
	public final boolean spawnInPyramids = false;
	public final boolean spawnInStrongholds = false;
	public final boolean spawnInVillages = false;
	
	public RecordData(String name, String soundName, String color) {
		this.name = name;
		this.soundName = soundName;
		this.color = color;
	}
	
	public String getName() { return name; }
	public String getSoundName() { return soundName; }
	public String getColor() { return color; }
	
	public void setSoundPrefix(String prefix) {
		if(soundName.indexOf(":") >= 0) {
			soundName = soundName.split(":")[1];
		}
		soundName = prefix + ":" + soundName;
	}
}
