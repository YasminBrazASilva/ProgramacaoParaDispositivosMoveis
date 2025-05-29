package com.example.af;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ReceitaAdapter extends RecyclerView.Adapter<ReceitaAdapter.ViewHolder> {
    private List<Receita> Receitas;

    public interface OnItemClickListener {
        void onItemClick(Receita Receita);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ReceitaAdapter(List<Receita> Receitas) {
        this.Receitas = Receitas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        Receita p = Receitas.get(pos);
        holder.txt1.setText(p.getNome());
        holder.txt2.setText("Ingredientes: " + p.getIngredientes());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(p);
            }
        });

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            private long lastClickTime = 0;

            @Override
            public boolean onTouch(View v, android.view.MotionEvent event) {
                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    long currentTime = System.currentTimeMillis();
//                    if (currentTime - lastClickTime < 300) {
//                        deletarReceita(p.getId(), holder.getAdapterPosition(), v);
//                    }
                    lastClickTime = currentTime;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return Receitas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2;
        public ViewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(android.R.id.text1);
            txt2 = itemView.findViewById(android.R.id.text2);
        }
    }
}
