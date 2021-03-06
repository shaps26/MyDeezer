package mydevmind.com.mydeezer.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mydevmind.com.mydeezer.R;
import mydevmind.com.mydeezer.fetcher.DownloadImagesTask;

/**
 * Created by Joan on 23/06/2014.
 */
public class MusicAdapter extends BaseAdapter{

    private Activity context;
    private ArrayList<Music> musics;

    public MusicAdapter(Activity context, ArrayList<Music> music){
        super();
        this.context= context;
        this.musics= music;
    }

    @Override
    public int getCount() {
        return musics.size();
    }

    @Override
    public Object getItem(int i) {
        return musics.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView= inflater.inflate(R.layout.music_list, null);
        TextView txtAlbum = (TextView) rowView.findViewById(R.id.txtListAlbum);
        ImageView imageViewAlbum = (ImageView) rowView.findViewById(R.id.imgListAlbum);
        ImageView imageViewFav = (ImageView) rowView.findViewById(R.id.imgFavListAlbum);
        txtAlbum.setText(musics.get(position).getTitle() + "\n" + musics.get(position).getArtist());
        if(musics.get(position).getCoverUrl()!="") {
            DownloadImagesTask imagesTask= new DownloadImagesTask(imageViewAlbum);
            imagesTask.execute(musics.get(position).getCoverUrl());
        }
        if(musics.get(position).isFavorite()){
            imageViewFav.setImageResource(android.R.drawable.star_on);
        }else{
            imageViewFav.setImageResource(android.R.drawable.star_off);
        }
        return rowView;
    }
    /*
    public void updateMembers(JSONObject jsonObject) {
        musics.clear();
        try {
            // Getting JSON Array node
            JSONArray tracks = jsonObject.getJSONArray("data");
            for (int i = 0; i < tracks.length(); i++) {
                JSONObject track = tracks.getJSONObject(i);
                Music m = new Music();
                m.setTitle(track.getString("title"));
                m.setArtist(track.getJSONObject("artist").getString("name"));
                m.setAlbum(track.getJSONObject("album").getString("title"));
                m.setDuration(track.getInt("duration"));
                m.setFavorite(false);
                m.setSampleUrl(track.getString("preview"));
                m.setLink(track.getString("link"));
                m.setCoverUrl(track.getJSONObject("album").getString("cover"));
                musics.add(m);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }*/
}
