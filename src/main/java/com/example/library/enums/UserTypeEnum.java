package com.example.library.enums;


public enum UserTypeEnum implements BaseEnum<Byte> {
  /**
   *
   */
  STUDENT("student", (byte) 0),
  TEACHER("teacher", (byte) 1),
  LIBRARIAN("librarian", (byte) 2),
  ADMIN("administrator", (byte) 3),
  ;

  private String desc;
  private Byte code;

  UserTypeEnum(String desc, Byte code) {
    this.desc = desc;
    this.code = code;
  }

  @Override
  public Byte getCode() {
    return this.code;
  }

  @Override
  public String getDesc() {
    return this.desc;
  }

  public String getKey() {
    return null;
  }


}
