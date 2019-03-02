/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.server.dl.dao;

import cz.cvut.fit.tjv.server.dl.entities.Vehicle;
import javax.ejb.Stateless;

/**
 *
 * @author tomastaro
 */
@Stateless
public class VehicleController extends AbstractCRUDController<Vehicle> {

    public VehicleController() {
        super(Vehicle.class);
    }

    @Override
    protected Integer getEntityId(Vehicle e) {
        return e.getId();
    }
}