package org.kamsoft.school.school.adapter;

public class Classess {
    private int id;
    boolean isSelected;
    public String _imgClass;
    public String _ClassName;
    ///mAdapter = new AddressListAdapter.MyViewHolder(


    public Classess() {
    }

    public Classess(int i, boolean b, int classroom, String lkjh) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String get_imgClass() {
        return _imgClass;
    }

    public void set_imgClass(String imgClass) {
        this._imgClass = imgClass;
    }


    public String get_ClassName() {
        return _ClassName;
    }

    public void set_ClassName(String ClassName) {
        this._ClassName = ClassName;
    }




}

