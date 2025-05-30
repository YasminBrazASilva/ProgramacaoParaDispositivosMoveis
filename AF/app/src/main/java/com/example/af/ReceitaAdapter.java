package com.example.af;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReceitaAdapter extends RecyclerView.Adapter<ReceitaAdapter.ViewHolder> {
    private List<Receita> Receitas;

    public interface OnItemClickListener {
        void onItemClick(Receita receita);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ReceitaAdapter(List<Receita> receitas) {
        this.Receitas = receitas;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        Receita r = Receitas.get(pos);
        holder.txt1.setText(r.getNome());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(r);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Receitas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt1;
        public ViewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(android.R.id.text1);
        }
    }
}