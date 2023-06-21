package com.example.medhelperandroid.elements;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.medhelperandroid.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;

    // Класс ViewHolder предоставляет ссылку на представления
    // используемые в данных RecyclerView
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    // Конструктор MyAdapter
    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Создает новые представления (вызывается layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // создайте новое представление
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Заменяет содержимое представления (вызывается layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - получите элемент из набора данных в этой позиции
        // - замените содержимое представления этими данными
        holder.textView.setText(mDataset[position]);
    }

    // Возвращает размер вашего набора данных (вызывается layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public void updateData(String[] newDataset) {
        mDataset = newDataset;
        notifyDataSetChanged();
    }

}

