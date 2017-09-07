package co.id.GoGolf.models.response.golf;

import java.util.ArrayList;

/**
 * Created by prumacadmin on 8/12/16.
 */
public class DataGolfDetail {
    private ArrayList<Promotion> promotion;

    private String designer;

    private String gname;

    private String establish;

    private String ing;

    private String url;

    private String info;

    private ArrayList<String> imagearr;

    private String par;

    private String address;

    private String area_name;

    private String gid;

    private String length;

    private String lat;

    private String holes;

    private String open;

    private String close;

    private String closedate;

    private String club_valet;

    private String caddy;

    private String carts;

    private String practice_facility;

    private String lesson;

    private String restaurant;

    private String spa;

    private String club_rental;

    private String shoe_rental;

    private String locker_room;

    private String night_golf;

    private String golf_shop;

    private String accomodation;

    private String health_club;

    public ArrayList<Promotion> getPromotion() {
        return promotion;
    }

    public void setPromotion(ArrayList<Promotion> promotion) {
        this.promotion = promotion;
    }

    public String getDesigner ()
    {
        return designer;
    }

    public void setDesigner (String designer)
    {
        this.designer = designer;
    }

    public String getGname ()
    {
        return gname;
    }

    public void setGname (String gname)
    {
        this.gname = gname;
    }

    public String getEstablish ()
    {
        return establish;
    }

    public void setEstablish (String establish)
    {
        this.establish = establish;
    }

    public String getIng ()
    {
        return ing;
    }

    public void setIng (String ing)
    {
        this.ing = ing;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getInfo ()
    {
        return info;
    }

    public void setInfo (String info)
    {
        this.info = info;
    }

    public ArrayList<String> getImagearr() {
        return imagearr;
    }

    public void setImagearr(ArrayList<String> imagearr) {
        this.imagearr = imagearr;
    }

    public String getPar ()
    {
        return par;
    }

    public void setPar (String par)
    {
        this.par = par;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getArea_name ()
    {
        return area_name;
    }

    public void setArea_name (String area_name)
    {
        this.area_name = area_name;
    }

    public String getGid ()
    {
        return gid;
    }

    public void setGid (String gid)
    {
        this.gid = gid;
    }

    public String getLength ()
    {
        return length;
    }

    public void setLength (String length)
    {
        this.length = length;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }

    public String getHoles ()
    {
        return holes;
    }

    public void setHoles (String holes)
    {
        this.holes = holes;
    }

    public String getOpen() {
        return open;
    }

    public String getClose() {
        return close;
    }

    public String getClosedate() {
        return closedate;
    }

    public String getClub_valet() {
        return club_valet;
    }

    public String getCaddy() {
        return caddy;
    }

    public String getCarts() {
        return carts;
    }

    public String getPractice_facility() {
        return practice_facility;
    }

    public String getLesson() {
        return lesson;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getSpa() {
        return spa;
    }

    public String getClub_rental() {
        return club_rental;
    }

    public String getShoe_rental() {
        return shoe_rental;
    }

    public String getLocker_room() {
        return locker_room;
    }

    public String getNight_golf() {
        return night_golf;
    }

    public String getGolf_shop() {
        return golf_shop;
    }

    public String getAccomodation() {
        return accomodation;
    }

    public String getHealth_club() {
        return health_club;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public void setClosedate(String closedate) {
        this.closedate = closedate;
    }

    public void setClub_valet(String club_valet) {
        this.club_valet = club_valet;
    }

    public void setCaddy(String caddy) {
        this.caddy = caddy;
    }

    public void setCarts(String carts) {
        this.carts = carts;
    }

    public void setPractice_facility(String practice_facility) {
        this.practice_facility = practice_facility;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public void setSpa(String spa) {
        this.spa = spa;
    }

    public void setClub_rental(String club_rental) {
        this.club_rental = club_rental;
    }

    public void setShoe_rental(String shoe_rental) {
        this.shoe_rental = shoe_rental;
    }

    public void setLocker_room(String locker_room) {
        this.locker_room = locker_room;
    }

    public void setNight_golf(String night_golf) {
        this.night_golf = night_golf;
    }

    public void setGolf_shop(String golf_shop) {
        this.golf_shop = golf_shop;
    }

    public void setAccomodation(String accomodation) {
        this.accomodation = accomodation;
    }

    public void setHealth_club(String health_club) {
        this.health_club = health_club;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [promotion = "+promotion+", designer = "+designer+", gname = "+gname+", establish = "+establish+", ing = "+ing+", url = "+url+", info = "+info+", imagearr = "+imagearr+", par = "+par+", address = "+address+", area_name = "+area_name+", gid = "+gid+", length = "+length+", lat = "+lat+", holes = "+holes+"]";
    }

}
