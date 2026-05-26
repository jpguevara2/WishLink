package com.example.wishlink;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GiftAdapter
        extends RecyclerView.Adapter<GiftAdapter.ViewHolder> {

    ArrayList<GiftItem> listaRegalos;

    public GiftAdapter(ArrayList<GiftItem> listaRegalos) {

        this.listaRegalos = listaRegalos;
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtItemNombre;
        TextView txtItemLink1;
        TextView txtItemLink2;

        ImageButton btnEliminar, btnEditar;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            txtItemNombre =
                    itemView.findViewById(R.id.txtItemNombre);

            txtItemLink1 =
                    itemView.findViewById(R.id.txtItemLink1);

            txtItemLink2 =
                    itemView.findViewById(R.id.txtItemLink2);

            btnEliminar =
                    itemView.findViewById(R.id.btnEliminar);

            btnEditar =
                    itemView.findViewById(R.id.btnEditar);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(
                        parent.getContext())
                .inflate(
                        R.layout.item_regalo,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        GiftItem item =
                listaRegalos.get(position);



        // Nombre
        holder.txtItemNombre.setText(
                item.getNombre());



        // LINK 1
        holder.txtItemLink1.setText("🔗 Link 1");

        holder.txtItemLink1.setOnClickListener(v -> {

            String link = item.getLink1().trim();

            // Arreglar espacios
            link = link.replace("https ://", "https://");
            link = link.replace("http ://", "http://");

            // Agregar https si falta
            if(!link.startsWith("http://") &&
                    !link.startsWith("https://")){

                link = "https://" + link;
            }

            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(link));

            v.getContext().startActivity(intent);
        });



        // LINK 2
        if(item.getLink2().isEmpty()){

            holder.txtItemLink2.setVisibility(View.GONE);

        }else{

            holder.txtItemLink2.setVisibility(View.VISIBLE);

            holder.txtItemLink2.setText("🔗 Link 2");

            holder.txtItemLink2.setOnClickListener(v -> {

                String link = item.getLink2().trim();

                // Arreglar espacios
                link = link.replace("https ://", "https://");
                link = link.replace("http ://", "http://");

                // Agregar https si falta
                if(!link.startsWith("http://") &&
                        !link.startsWith("https://")){

                    link = "https://" + link;
                }

                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(link));

                v.getContext().startActivity(intent);
            });
        }



        // EDITAR
        holder.btnEditar.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            v.getContext(),
                            Agregar.class);

            intent.putExtra("modo", "editar");

            intent.putExtra(
                    "nombre",
                    item.getNombre());

            intent.putExtra(
                    "link1",
                    item.getLink1());

            intent.putExtra(
                    "link2",
                    item.getLink2());

            intent.putExtra(
                    "posicion",
                    position);

            ((Menu)v.getContext())
                    .launcher.launch(intent);
        });



        // ELIMINAR
        holder.btnEliminar.setOnClickListener(v -> {

            listaRegalos.remove(position);

            notifyItemRemoved(position);

            notifyItemRangeChanged(
                    position,
                    listaRegalos.size());
        });
    }

    @Override
    public int getItemCount() {

        return listaRegalos.size();
    }
}