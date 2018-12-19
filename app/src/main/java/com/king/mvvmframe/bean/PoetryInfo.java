package com.king.mvvmframe.bean;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class PoetryInfo implements Parcelable {


    /**
     * title : 幽居
     * content : 小舫藤为缆，幽居竹织门。|短篱围藕荡，细路入桑村。|鱼脍槎头美，醅倾粥面浑。|残年谢轩冕，犹足号黎元。
     * authors : 陆游
     */

    private String title;
    private String content;
    private String authors;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "PoetryInfo{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", authors='" + authors + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.authors);
    }

    public PoetryInfo() {
    }

    protected PoetryInfo(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.authors = in.readString();
    }

    public static final Creator<PoetryInfo> CREATOR = new Creator<PoetryInfo>() {
        @Override
        public PoetryInfo createFromParcel(Parcel source) {
            return new PoetryInfo(source);
        }

        @Override
        public PoetryInfo[] newArray(int size) {
            return new PoetryInfo[size];
        }
    };
}
