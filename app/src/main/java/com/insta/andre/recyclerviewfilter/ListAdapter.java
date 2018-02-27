package com.insta.andre.recyclerviewfilter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements Filterable {
    private ArrayList<Person> persons;
    private ArrayList<Person> personsFilter;
    private CustomFilter mFilter;
    public OnItemClickListener itemClickListener;

    // Reemplaza en contenido de la vista
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
       //viewHolder.nameTextView.setText(personsFilter.get(position).getName());
        //viewHolder.descriptionTextView.setText(personsFilter.get(position).getDescription());
        //viewHolder.colorLl.setBackgroundColor(Color.parseColor(personsFilter.get(position).getColor()));
        viewHolder.bind(personsFilter.get(position).getName(), personsFilter.get(position).getDescription(), personsFilter.get(position).getColor(), itemClickListener);

    }

    // Retorna el tamano de nuestra data
    @Override
    public int getItemCount() {
        return personsFilter.size();
    }

    // Provee una referencia a cada item dentro de una vista y acceder a ellos facilmente
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Cada uno de los elementos de mi vista
        public TextView nameTextView, descriptionTextView;
        public CardView cardView;
        public LinearLayout colorLl;
        public RelativeLayout parentBodyRl;

        public ViewHolder(View v) {
            super(v);
            parentBodyRl = (RelativeLayout) v.findViewById(R.id.parent_body_rl);
            cardView = (CardView) v.findViewById(R.id.card_view);
            nameTextView = (TextView) v.findViewById(R.id.name_tv);
            descriptionTextView = (TextView) v.findViewById(R.id.description_tv);
            colorLl = (LinearLayout) v.findViewById(R.id.color_ll);
        }

        public void bind(final String name, final String description, final String color, final OnItemClickListener listener) {
            this.nameTextView.setText(name);

            this.descriptionTextView.setText(description+"a");
            this.colorLl.setBackgroundColor(Color.parseColor(color));


            this.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(v.getContext(), "ssss"+name , Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String name, int position);
    }

    // Constructor
    public ListAdapter(ArrayList<Person> persons) {

        this.persons = persons;
        this.personsFilter = new ArrayList<>();
        this.personsFilter.addAll(persons);
        this.mFilter = new CustomFilter(ListAdapter.this);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // inflo la vista (vista padre)
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_adapter, parent, false);
        // creo el grupo de vistas
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }


    /*Filtro*/
    public class CustomFilter extends Filter {
        private ListAdapter listAdapter;

        private CustomFilter(ListAdapter listAdapter) {
            super();
            this.listAdapter = listAdapter;


        }


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            personsFilter.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                personsFilter.addAll(persons);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final Person person : persons) {
                    if (person.getName().toLowerCase().contains(filterPattern)) {
                        personsFilter.add(person);
                    }
                }
            }


            results.values = personsFilter;
            results.count = personsFilter.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            this.listAdapter.notifyDataSetChanged();


        }
    }

}