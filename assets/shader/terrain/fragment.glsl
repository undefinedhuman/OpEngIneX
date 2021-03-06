#version 140

const int MAX_POINT_LIGHTS = 5;

in vec4 positionInWorldSpace;
in vec2 passTextureCoords;
in vec3 surfaceNormal;
in float fogFactor;
in vec4 shadowCoords;

out vec4 fragColor;

uniform sampler2D textureSampler;

struct DirectionalLight {
    float intensity;
    vec3 direction;
    vec3 color;
};

uniform DirectionalLight sun;

struct PointLight {
    float intensity;
    vec3 position;
    vec3 color;
    vec3 attenuation;
};

uniform sampler2D shadowMapTexture;

uniform PointLight pointLights[MAX_POINT_LIGHTS];

uniform vec3 fogColor;

uniform vec3 cameraPosition;

uniform float ambientValue;
uniform float specularStrength;
uniform float shineDamper;

const int pixelSample = 1;
const float totalSamples = (pixelSample * 2.0 + 1.0) * (pixelSample * 2.0 + 1.0);

vec3 calcLightColor(float shadowFactor, float lightIntensity, vec3 lightColor, vec3 unitLight, vec3 unitCamera, vec3 unitNormal) {
    // Diffuse
    float brightness = max(dot(unitNormal, unitLight), 0.0);
    vec3 diffuse = brightness * lightColor * lightIntensity * shadowFactor;

    // Specular
    vec3 reflectedLight = normalize(reflect(-unitLight, unitNormal));
    float specularFactor = pow(max(dot(unitCamera, reflectedLight), 0.0), shineDamper);
    vec3 specular = specularStrength * lightIntensity * specularFactor * lightColor;

    return (diffuse + specular);
}

vec3 calcPointLight(float shadowFactor, PointLight light, vec3 unitCamera, vec3 unitNormal) {
    vec3 lightDirection = light.position - positionInWorldSpace.xyz;
    vec3 ligthColor = calcLightColor(shadowFactor, light.intensity, light.color, normalize(lightDirection), unitCamera, unitNormal);

    float distance = length(lightDirection);
    float attentuation = light.attenuation.x + light.attenuation.y * distance + light.attenuation.z * distance * distance;
    return ligthColor / attentuation;
}

vec3 calcDirectionalLight(float shadowFactor, DirectionalLight light, vec3 unitCamera, vec3 unitNormal) {
    return calcLightColor(shadowFactor, light.intensity, light.color, normalize(light.direction), unitCamera, unitNormal);
}

void main() {

    float total = 0.0;

    float shadowMapSize = 4096;
    float sampleSize = 1.0 / shadowMapSize;

    for(int x = -pixelSample; x <= pixelSample; x++) {
        for(int y = -pixelSample; y <= pixelSample; y++) {
            if(shadowCoords.z > texture(shadowMapTexture, shadowCoords.xy + vec2(x, y) * sampleSize).r + 0.002) {
                total += 1.0;
            }
        }
    }

    total /= totalSamples;

    float shadowFactor = 1.0 - (total * shadowCoords.w);

    vec3 unitCamera = normalize(cameraPosition - positionInWorldSpace.xyz);
    vec3 unitNormal = normalize(surfaceNormal);

    vec3 diffuseSpecular = calcDirectionalLight(shadowFactor, sun, unitCamera, unitNormal);

    for(int i = 0; i < MAX_POINT_LIGHTS; i++) {
        if(pointLights[i].intensity > 0) {
            diffuseSpecular += calcPointLight(shadowFactor, pointLights[i], unitCamera, unitNormal);
        }
    }

    fragColor =  vec4(vec3(ambientValue) + diffuseSpecular, 1) * texture(textureSampler, passTextureCoords);
    fragColor = mix(vec4(fogColor, 1), fragColor, fogFactor);
}
