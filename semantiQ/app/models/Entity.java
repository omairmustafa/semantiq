package models;

public class Entity {

	private String entityName;
	private String entityType;
	private String entityOccuranceCount;
	private String entityWebsite;
	private String entityRelevance;
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getEntityOccuranceCount() {
		return entityOccuranceCount;
	}
	public void setEntityOccuranceCount(String entityOccuranceCount) {
		this.entityOccuranceCount = entityOccuranceCount;
	}
	public String getEntityWebsite() {
		return entityWebsite;
	}
	public void setEntityWebsite(String entityWebsite) {
		this.entityWebsite = entityWebsite;
	}
	public String getEntityRelevance() {
		return entityRelevance;
	}
	public void setEntityRelevance(String entityRelevance) {
		this.entityRelevance = entityRelevance;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.entityName + " " + this.entityType + " " + this.entityOccuranceCount + " " + this.entityWebsite + " " + this.entityRelevance ;
	}
	
}
