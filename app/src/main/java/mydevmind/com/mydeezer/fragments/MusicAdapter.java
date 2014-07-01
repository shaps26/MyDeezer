package mydevmind.com.mydeezer.fragments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mydevmind.com.mydeezer.R;
import mydevmind.com.mydeezer.model.fetcher.VolleyConnectionManager;
import mydevmind.com.mydeezer.model.modelObject.Music;

/**
 * Created by Joan on 23/06/2014.
 * MusicAdapter for the ViewList of Musics
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
        View rowView;
        rowView = inflater.inflate(R.layout.music_list, null);
        TextView txtAlbum;
        txtAlbum = (TextView) (rowView != null ? rowView.findViewById(R.id.txtListAlbum) : null);
        NetworkImageView imageViewAlbum = (NetworkImageView) (rowView != null ? rowView.findViewById(R.id.imgListAlbum) : null);
        ImageView imageViewFav = (ImageView) (rowView != null ? rowView.findViewById(R.id.imgFavListAlbum) : null);
        assert txtAlbum != null;
        txtAlbum.setText(musics.get(position).getTitle() + "\n" + musics.get(position).getArtist());
        if(!musics.get(position).getCoverUrl().equals("")) {
            imageViewAlbum.setImageUrl(musics.get(position).getCoverUrl(), VolleyConnectionManager.getmVolleyImageLoader());
        }
        if(musics.get(position).isFavorite()){
            imageViewFav.setImageResource(android.R.drawable.star_on);
        }else{
            imageViewFav.setImageResource(android.R.drawable.star_off);
        }
        return rowView;
    }

    public void updateMembers(JSONObject jsonObject) {
        musics.clear();
        try {
            // Getting JSON Array node
            JSONArray tracks = jsonObject.getJSONArray("data");
            for (int i = 0; i < tracks.length(); i++) {
                JSONObject track = tracks.getJSONObject(i);
                Music m = new Music(String.valueOf(track.getInt("id")));
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
    }
}
