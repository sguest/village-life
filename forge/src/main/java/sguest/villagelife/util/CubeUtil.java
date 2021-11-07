package sguest.villagelife.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ModelBuilder;

public class CubeUtil {
    public static ModelBuilder<BlockModelBuilder>.ElementBuilder modelElement(BlockModelBuilder builder, Cube cube) {
        return builder.element().from(cube.getX1(), cube.getY1(), cube.getZ1()).to(cube.getX2(), cube.getY2(), cube.getZ2());
    }

    public static VoxelShape getVoxel(Cube cube) {
        return Block.makeCuboidShape(cube.getX1(), cube.getY1(), cube.getZ1(), cube.getX2(), cube.getY2(), cube.getZ2());
    }

    public static List<VoxelShape> getVoxels(Cube ... cubes) {
        return Stream.of(cubes).map(CubeUtil::getVoxel).collect(Collectors.toList());
    }

    public static Cube rotate(Cube cube) {
        return new Cube(16 - cube.getZ1(), cube.getY1(), cube.getX1(), 16 - cube.getZ2(), cube.getY2(), cube.getX2());
    }

    public static Cube[] rotate(Cube ... cubes) {
        return Stream.of(cubes).map(CubeUtil::rotate).toArray(Cube[]::new);
    }

    public static Map<Direction, VoxelShape> getHorizontalShapes(Cube ... cubes) {
        Map<Direction, VoxelShape> shapes = new HashMap<>();
        shapes.put(Direction.NORTH, friendlyOr(getVoxels(cubes)));
        cubes = rotate(cubes);
        shapes.put(Direction.EAST, friendlyOr(getVoxels(cubes)));
        cubes = rotate(cubes);
        shapes.put(Direction.SOUTH, friendlyOr(getVoxels(cubes)));
        cubes = rotate(cubes);
        shapes.put(Direction.WEST, friendlyOr(getVoxels(cubes)));

        return shapes;
    }

    private static VoxelShape friendlyOr(List<VoxelShape> shapes) {
        VoxelShape first = shapes.remove(0);
        return VoxelShapes.or(first, shapes.stream().toArray(VoxelShape[]::new));
    }

    public static class Cube {
        private final float x1;
        private final float x2;
        private final float y1;
        private final float y2;
        private final float z1;
        private final float z2;

        public Cube(float x1, float y1, float z1, float x2, float y2, float z2) {
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;
            this.x2 = x2;
            this.y2 = y2;
            this.z2 = z2;
        }

        public float getX1() {
            return this.x1;
        }

        public float getY1() {
            return this.y1;
        }

        public float getZ1() {
            return this.z1;
        }

        public float getX2() {
            return this.x2;
        }

        public float getY2() {
            return this.y2;
        }

        public float getZ2() {
            return this.z2;
        }
    }
}
