public class Resolution {

    private float height;
    private float width;

    public Resolution() {
        this.width = 0;
        this.height = 0;
    }

    public Resolution(float w, float h) {
        this.width = w;
        this.height = h;
    }

    public Resolution(Resolution r) {
        this.width = r.getWidth();
        this.height = r.getHeight();
    }

    public Resolution(String[] res) {
        this.width = Float.parseFloat(res[0]);
        this.height = Float.parseFloat(res[1]);
    }

    public float getWidth() {return this.width;}

    public float getHeight() {return this.height;}

    public void setWidth(float width) {this.width = width;}

    public void setHeight(float height) {this.height = height;}

    public float getRes(){
        double w = this.width;
        double h = this.height;

        return  (float) Math.sqrt(Math.pow(h,2.0) + Math.pow(w,2.0));
    }

    public Resolution clone(){return new Resolution(this);}

    public String toString() {
        return "\nResolution: " + this.width + " x " + this.height;
    }

    public boolean equals(Object o) {
        if (o==this) return true;
        if (o==null || o.getClass()!= this.getClass()) return false;
        Resolution res = (Resolution) o;
        return (this.width == res.getWidth() &&
                this.height == res.getHeight());
    }
}
