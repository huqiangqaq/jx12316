package com.nxt.ott.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.activity.FbgqDetailActivity;
import com.nxt.ott.util.SerializableObject;
import com.nxt.zyl.util.HttpUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class FbgqAdapter extends BaseAdapter {
	private List<HashMap<String,Object>> list;
	private int resource;
	private File pathfile;
	private LayoutInflater inflater;
	Context context;
	private static final String LAG = "log";

	public FbgqAdapter(Context context, List<HashMap<String,Object>> list,
					   int resource,File pathfile) {
		this.list=list;
		this.resource = resource;
		this.pathfile=pathfile;
		this.context=context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyHolder holder;
		if(convertView==null){
			holder=new MyHolder();
			convertView= inflater.inflate(resource, null);
			holder.imageview=(ImageView) convertView.findViewById(R.id.fbgq_list_image);

			holder.titletextone=(TextView) convertView.findViewById(R.id.fbgq_list_titleone);
			holder.titletexttwo=(TextView) convertView.findViewById(R.id.fbgq_list_titletwo);
			holder.abouttext=(TextView) convertView.findViewById(R.id.fbgq_list_about);
			holder.typeimage=(ImageView) convertView.findViewById(R.id.fbgq_list_type);
			convertView.setTag(holder);

		}else{
			holder=(MyHolder) convertView.getTag();
		}
		/*map.put("Date", jb.getString("Date"));
		// map.put("id", jb.getString("id"));
		map.put("ProductName", jb.getString("ProductName"));
		map.put("Describe", jb.getString("Describe"));
		map.put("imghttpurl", jb.getString("imghttpurl"));
		map.put("TypeId", jb.getInt("TypeId"));*/
		final HashMap map=list.get(position);
		String title = map.get("ProductName").toString();
		String content = map.get("Describe").toString();
		String creatTime = map.get("Date").toString();
		String supporlyorDemand = map.get("TypeId").toString();
		Log.i("supporlyorDemand", supporlyorDemand);
		String imageUrl = map.get("imghttpurl").toString();
		if(title!=null){
			holder.titletexttwo.setText(title);
		}if(title!=null){
			holder.abouttext.setText(title);
		}if(supporlyorDemand!=null){
			if("1".equals(supporlyorDemand)){
				holder.titletextone.setText("【供应】");
				holder.titletextone.setTextColor(context.getResources().getColor(R.color.btn_green_noraml));
				holder.typeimage.setBackgroundResource(R.mipmap.need);


			}else if("2".equals(supporlyorDemand)){
				holder.titletextone.setText("【求购】");
				holder.titletextone.setTextColor(context.getResources().getColor(R.color.redbg));
				holder.typeimage.setBackgroundResource(R.mipmap.supply);
			}
		}if(imageUrl!=null){
			holder.imageview.setTag(imageUrl);
			asyncImageLoad(holder.imageview,imageUrl);
		}
		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, FbgqDetailActivity.class);
				SerializableObject sm = new SerializableObject();
				sm.setHmap(map);
				intent.putExtra("news", sm);
				context.startActivity(intent);

			}
		});
		return convertView;
	}
	class MyHolder{
		TextView titletextone;
		TextView titletexttwo;
		TextView abouttext;
		ImageView typeimage;
		ImageView imageview;
	}
	private void asyncImageLoad(ImageView imageView1, String imageUrl) {
		AsyncImageTask imageTask=new AsyncImageTask(imageView1,imageUrl);
		imageTask.execute(imageUrl);
	}
	private final class AsyncImageTask extends AsyncTask<String, Integer, Uri>{


		ImageView imageview;
		String imageurl;
		public AsyncImageTask(ImageView imageview,String imageurl) {
			this.imageview=imageview;
			this.imageurl=imageurl;
		}

		@Override
		protected Uri doInBackground(String... params) {//
			try {
				return  HttpUtils.getImageUrl2(params[0], pathfile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Uri result) {//
			if(result!=null && imageview!=null){
				if(imageview.getTag().toString().equals(imageurl)){
					imageview.setImageURI(result);
				}
			}
			super.onPostExecute(result);
		}

	}
}
