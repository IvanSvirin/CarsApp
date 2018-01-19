package com.luxoft.carsapp.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luxoft.carsapp.R;
import com.luxoft.carsapp.presentation.model.CarModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.CarsViewHolder> {

  public interface OnItemClickListener {
    void onCarItemClicked(CarModel carModel);
  }

  private List<CarModel> carsCollection;
  private final LayoutInflater layoutInflater;

  private OnItemClickListener onItemClickListener;

  @Inject
  public CarsAdapter(Context context) {
    this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.carsCollection = Collections.emptyList();
  }

  @Override
  public int getItemCount() {
    return (this.carsCollection != null) ? this.carsCollection.size() : 0;
  }

  @Override
  public CarsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = this.layoutInflater.inflate(R.layout.item_car, parent, false);
    return new CarsViewHolder(view);
  }

  @Override
  public void onBindViewHolder(CarsViewHolder holder, final int position) {
    final CarModel carModel = this.carsCollection.get(position);
    holder.textViewModel.setText(carModel.getModel());
    holder.textViewManufacturer.setText(carModel.getManufacturer());
    holder.textViewPrice.setText(String.valueOf(carModel.getPrice()));
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (CarsAdapter.this.onItemClickListener != null) {
          CarsAdapter.this.onItemClickListener.onCarItemClicked(carModel);
        }
      }
    });
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  public void setCarsCollection(Collection<CarModel> carsCollection) {
    this.validateCarsCollection(carsCollection);
    this.carsCollection = (List<CarModel>) carsCollection;
    this.notifyDataSetChanged();
  }

  public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  private void validateCarsCollection(Collection<CarModel> carModelCollection) {
    if (carModelCollection == null) {
      throw new IllegalArgumentException("The list cannot be null");
    }
  }

  static class CarsViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_model)
    TextView textViewModel;
    @Bind(R.id.tv_manufacturer)
    TextView textViewManufacturer;
    @Bind(R.id.tv_price)
    TextView textViewPrice;

    public CarsViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
