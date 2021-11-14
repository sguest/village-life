package sguest.villagelife.resources;

import net.devtech.arrp.json.models.JFace;
import net.devtech.arrp.json.models.JFaces;
import net.minecraft.util.math.Direction;

public class JFacesCustom extends JFaces {
    public JFacesCustom sides(JFace face) {
        this.north(face).south(face).east(face).west(face);
        return this;
    }

    public JFacesCustom sidesWithCulling(JFace face) {
        this.north(face.clone().cullface(Direction.NORTH))
            .south(face.clone().cullface(Direction.SOUTH))
            .east(face.clone().cullface(Direction.EAST))
            .west(face.clone().cullface(Direction.WEST));
        return this;
    }
}
